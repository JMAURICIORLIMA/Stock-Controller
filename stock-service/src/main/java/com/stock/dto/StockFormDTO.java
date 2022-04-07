package com.stock.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockFormDTO {
	
	private Long productId;
	private double price;
	private double exitPrice;
	private int stockQuantity;
    
}
