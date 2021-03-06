package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Controller("/")
public class DemoController {
    @Autowired
    StoreRepository storeRepository;

    @Autowired
    KafkaTemplate<String,SuperStore> kafkaTemplate;

    @Value(value = "${kafka.topic.name}")
    private String topicName;

    private static final HashMap<String, ArrayList<List<String>>> dataset = new HashMap<>();

    @PostConstruct
    public void init() {
        try(BufferedReader br = new BufferedReader(new FileReader(ResourceUtils.getFile("classpath:superstore.csv")))){
            String line;
            while ((line = br.readLine()) != null){
                try{
                    String[] values = line.split(",");
                    String year = values[2].split("/")[2];
                    ArrayList<List<String>> temp = dataset.getOrDefault(year,new ArrayList<List<String>>());
                    temp.add(Arrays.asList(values));
                    dataset.put(year,temp);
                } catch (Exception e){
                    System.out.println(line);
                    e.printStackTrace();
                }
            }
            for(String s: dataset.keySet()) {
                System.out.println(s+" == "+dataset.get(s).size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("publish/{year}")
    public void saveToCassandra(@PathVariable String year){
        ArrayList<List<String>> lists = dataset.get(year);
        if(lists != null && !lists.isEmpty()){
            //push to kafka
            for(List<String> list:lists) {
                SuperStore.SuperStoreBuilder superStoreBuilder = SuperStore.builder();
                superStoreBuilder.uid(UUID.randomUUID());
                superStoreBuilder.Row_ID(list.get(0));
                superStoreBuilder.Order_ID(list.get(1));
                superStoreBuilder.Order_Date(list.get(2));
                superStoreBuilder.Ship_Date(list.get(3));
                superStoreBuilder.Ship_Mode(list.get(4));
                superStoreBuilder.Customer_ID(list.get(5));
                superStoreBuilder.Customer_Name(list.get(6));
                superStoreBuilder.Segment(list.get(7));
                superStoreBuilder.Country(list.get(8));
                superStoreBuilder.City(list.get(9));
                superStoreBuilder.State(list.get(10));
                superStoreBuilder.Postal_Code(list.get(11));
                superStoreBuilder.Region(list.get(12));
                superStoreBuilder.Product_ID(list.get(13));
                superStoreBuilder.Category(list.get(14));
                superStoreBuilder.Sub_Category(list.get(15));
                superStoreBuilder.Product_Name(list.get(16));
                superStoreBuilder.Sales(list.get(17));
                superStoreBuilder.Quantity(list.get(18));
                superStoreBuilder.Discount(list.get(19));
                superStoreBuilder.Profit(list.get(20));

                //publish to kafka
                ListenableFuture<SendResult<String,SuperStore>> future = kafkaTemplate.send(topicName,superStoreBuilder.build() );
                future.addCallback(new ListenableFutureCallback<SendResult<String, SuperStore>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        System.out.println("Unable to publish "+ex.getMessage()+ex.getStackTrace());
                    }

                    @Override
                    public void onSuccess(SendResult<String, SuperStore> result) {
                        System.out.println("Message ["+superStoreBuilder.build()+"] delivered with offset {"+result.getRecordMetadata().offset()+"} and {"+result+"}");

                    }
                });
            }
        }
    }
    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}",containerFactory = "containerFactory")
    public void consume(SuperStore superStore){
        System.out.println("Consuming ::::::: ");
        System.out.println(superStore);
        storeRepository.save(superStore);
    }
}
