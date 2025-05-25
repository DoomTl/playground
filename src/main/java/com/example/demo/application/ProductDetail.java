package com.example.demo.application;

import lombok.Data;

import java.util.List;

/**
 * ProductDetail
 *
 * @author tl
 * @since 20250522 22:37
 */
@Data
public class ProductDetail {

	private String title1;

	private String title2;

	private String table1;

	private String table2;

	private List<Item> details;

	@Data
	public static class Item {
		private String prop1;
		private String prop2;
		private String prop3;
		private String prop4;
		private String prop5;
		private boolean merge1Start;
		private boolean merge2Start;

		public static Item of(int i) {
			Item item = new Item();
			item.setProp1("prop1_" + i / 4);
			item.setProp2("prop2_" + i / 2);
			item.setProp3("prop3_" + i);
			item.setProp4("prop4_" + i);
			item.setProp5("prop5_" + i);
			return item;
		}
	}
}