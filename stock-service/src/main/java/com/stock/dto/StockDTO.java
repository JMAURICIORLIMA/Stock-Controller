package com.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {

	private Long productId;
	private double price;
	private double exitPrice;
	private int stockQuantity;
	
}
