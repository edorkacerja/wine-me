package com.dmbteam.catalogapp.cart;

import android.content.Context;

import com.dmbteam.catalogapp.cmn.Product;
import com.dmbteam.catalogapp.settings.AppSettings;
import com.dmbteam.catalogapp.util.AutoIncrementGenerator;
import com.dmbteam.catalogapp.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class CartManager.
 */
public class CartManager {

	/** The instance. */
	private static CartManager instance;

	/** The All items. */
	private List<CartItem> mAllItems;

	/**
	 * Gets the single instance of CartManager.
	 * 
	 * @return single instance of CartManager
	 */
	public static CartManager getInstance() {

		if (instance == null) {
			synchronized (CartManager.class) {
				if (instance == null) {
					instance = new CartManager();
				}
			}
		}

		return instance;
	}

	/**
	 * Instantiates a new cart manager.
	 */
	private CartManager() {
		mAllItems = new ArrayList<CartItem>();
	}

	/**
	 * Adds the product to items.
	 * 
	 * @param product
	 *            the product
	 */
	public void addProductToItems(Product product) {
		this.mAllItems.add(new CartItem(product));
	}

	/**
	 * Clear cart.
	 */
	public void clearCart() {
		this.mAllItems.clear();
	}

	/**
	 * Check existance.
	 * 
	 * @param productToCheck
	 *            the product to check
	 * @return true, if successful
	 */
	public boolean checkExistance(Product productToCheck) {
		for (int i = 0; i < mAllItems.size(); i++) {
			if (mAllItems.get(i).getProduct().getId() == productToCheck.getId()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Gets the all items.
	 * 
	 * @return the all items
	 */
	public List<CartItem> getAllItems() {
		return mAllItems;
	}

	/**
	 * Increment quantity of product.
	 * 
	 * @param productId
	 *            the product id
	 */
	public void incrementQuantityOfProduct(int productId) {
		for (int i = 0; i < getAllItems().size(); i++) {
			if (getAllItems().get(i).getProduct().getId() == productId) {
				getAllItems().get(i).incrementQuantity();
				break;
			}
		}
	}

	/**
	 * Decrement quantity of product.
	 * 
	 * @param productId
	 *            the product id
	 */
	public void decrementQuantityOfProduct(int productId) {
		for (int i = 0; i < getAllItems().size(); i++) {
			if (getAllItems().get(i).getProduct().getId() == productId) {
				getAllItems().get(i).decrementQuantity();
				break;
			}
		}
	}

	/**
	 * Gets the saved value.
	 * 
	 * @return the saved value
	 */
	public String getSavedValue() {
		double value = 0;

		for (int i = 0; i < getAllItems().size(); i++) {
			CartItem currentCartItem = getAllItems().get(i);
			Product currentProduct = currentCartItem.getProduct();

			if (currentProduct.getDiscount() > 0) {
				value += currentCartItem.getQuantity()
						* (currentProduct.getPrice() - currentProduct
								.getDiscountedPrice());
			}
		}

		return Utils.mFormatter.format(value).replace(",", "");
	}

	/**
	 * Gets the sub total.
	 * 
	 * @return the sub total
	 */
	public String getSubTotalString() {
		double value = getSubTotalDouble();
		return Utils.mFormatter.format(value);
	}

	private double getSubTotalDouble() {
		double value = 0;

		for (int i = 0; i < getAllItems().size(); i++) {
			CartItem currentCartItem = getAllItems().get(i);
			Product currentProduct = currentCartItem.getProduct();

			if (currentProduct.getDiscount() > 0) {
				value += currentCartItem.getQuantity()
						* currentProduct.getDiscountedPrice();
			} else {
				value += currentCartItem.getQuantity()
						* currentProduct.getPrice();
			}
		}
		value = value / (1 + AppSettings.VAT);
		return value;
	}

	/**
	 * Gets the vat.
	 * 
	 * @return the vat
	 */
	public String getVatString() {
		return Utils.mFormatter.format(getVatDouble());
	}

	public double getVatDouble() {
		double value = getSubTotalDouble()
				* AppSettings.VAT;
		return value;
	}

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public String getTotal() {
		return Utils.mFormatter.format(getSubTotalDouble()
				+ getVatDouble());
	}

	/**
	 * Removes the item.
	 * 
	 * @param position
	 *            the position
	 */
	public void removeItem(int position) {
		getAllItems().remove(position);
	}

	/**
	 * Generate string for mail.
	 * 
	 * @param name
	 *            the name
	 * @param mail
	 *            the mail
	 * @param phone
	 *            the phone
	 * @param comment
	 *            the comment
	 * @return the string
	 */
	public String generateStringForMail(Context context, String name,
			String mail, String phone, String comment) {
		String result = "";

		result += "Order Number: "
				+ AutoIncrementGenerator.getIncrementValue(context) + "\n\n";

		result += "Name: ";
		result += name;
		result += "\n";

		result += "E-mail: ";
		result += mail;
		result += "\n";

		result += "Phone: ";
		result += phone;
		result += "\n";

		if (!comment.isEmpty()) {
			result += "Comment: ";
			result += comment;
			result += "\n";
		}

		result += "\n";
		result += "\n";

		for (int i = 0; i < getAllItems().size(); i++) {
			CartItem currentCartItem = getAllItems().get(i);
			Product currentProduct = currentCartItem.getProduct();

			result += currentProduct.getTitle();
			result += "\t\t\t\t\t";
			result += currentCartItem.getQuantity();
			result += "x";
			result += "\t\t\t";

			String price = "";
			if (currentProduct.getDiscount() > 0) {
				price = Utils.mFormatter.format(currentProduct
						.getDiscountedPrice());
			} else {
				price = Utils.mFormatter.format(currentProduct.getPrice());
			}

			result += price;
			result += "\n";

		}

		result += "\n";
		result += "\n";

		result += "Total: " + getTotal();

		return result;
	}
}
