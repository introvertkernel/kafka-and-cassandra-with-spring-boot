package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuperStore {
    @PrimaryKey
    UUID uid;
    String Row_ID;
    String Order_ID;
    String Order_Date;
    String Ship_Date;
    String Ship_Mode;
    String Customer_ID;
    String Customer_Name;
    String Segment;
    String Country;
    String City;
    String State;
    String Postal_Code;
    String Region;
    String Product_ID;
    String Category;
    String Sub_Category;
    String Product_Name;
    String Sales;
    String Quantity;
    String Discount;
    String Profit;
}
