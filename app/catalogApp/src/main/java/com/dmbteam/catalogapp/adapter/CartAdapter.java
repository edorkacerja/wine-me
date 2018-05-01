package com.dmbteam.catalogapp.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmbteam.catalogapp.R;
import com.dmbteam.catalogapp.cart.CartItem;
import com.dmbteam.catalogapp.cart.CartManager;
import com.dmbteam.catalogapp.cmn.Product;
import com.dmbteam.catalogapp.fragment.FragmentCart;
import com.dmbteam.catalogapp.settings.AppSettings;
import com.dmbteam.catalogapp.util.ImageOptionsBuilder;
import com.dmbteam.catalogapp.util.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * The Class CartAdapter.
 */
public class CartAdapter extends ArrayAdapter<CartItem> {

	public CartAdapter(Context context, List<CartItem> objects, FragmentCart fragmentCart){
		super(context, 0, objects);

		this.mFragmentCart = fragmentCart;

		this.mDisplayImageOptions = ImageOptionsBuilder
				.buildGeneralImageOptions(false, R.drawable.home_nexus9);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_cart, parent,
				false);

		ImageView imageViewPicture = (ImageView) convertView
				.findViewById(R.id.list_item_cart_image);
		TextView title = (TextView) convertView.findViewById(R.id.list_item_cart_title);
		TextView priceDiscount = (TextView) convertView
				.findViewById(R.id.list_item_cart_price_discount);
		TextView priceRealUnderlined = (TextView) convertView
				.findViewById(R.id.list_item_cart_price_price_underlined);
		ImageView imageViewPlus = (ImageView) convertView
				.findViewById(R.id.list_item_cart_image_plus);
		ImageView imageViewMinus = (ImageView) convertView
				.findViewById(R.id.list_item_cart_image_minus);
		TextView quantity = (TextView) convertView
				.findViewById(R.id.list_item_cart_qty_value);

		CartItem currentCartItem = getItem(position);
		Product currentProduct = currentCartItem.getProduct();

		if (Product.isNetworkResource(currentProduct.getPhotos(getContext()).get(0))) {
			ImageLoader.getInstance().displayImage(
					currentProduct.getPhotos(getContext()).get(0), imageViewPicture,
					mDisplayImageOptions);
		} else {
			imageViewPicture.setImageDrawable(getContext().getResources()
					.getDrawable(Product.getDrawableId(getContext(), currentProduct.getPhotos(getContext()).get(0))));
		}

		title.setText(currentProduct.getTitle());

		String realPrice = Utils.mFormatter.format(currentProduct.getPrice())
				+ AppSettings.CURRENCY;
		if (currentProduct.getDiscount() > 0) {

			String discountedPrice = Utils.mFormatter.format(currentProduct
					.getDiscountedPrice()) + AppSettings.CURRENCY;

			priceDiscount.setText(discountedPrice);

			priceRealUnderlined.setPaintFlags(priceRealUnderlined
					.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			priceRealUnderlined.setText(realPrice);

			priceDiscount.setVisibility(View.VISIBLE);
			priceRealUnderlined.setVisibility(View.VISIBLE);

		} else {
			priceDiscount.setVisibility(View.GONE);

			priceRealUnderlined.setText(realPrice);
			priceRealUnderlined.setVisibility(View.VISIBLE);
		}

		quantity.setText("" + currentCartItem.getQuantity());

		imageViewPlus.setOnClickListener(new IncrementQuantityListener(
				currentProduct.getId()));
		imageViewMinus.setOnClickListener(new DecrementQuantityListener(
				currentProduct.getId()));

		return convertView;
	}




	/** The Display image options. */
	private DisplayImageOptions mDisplayImageOptions;

	/** The Fragment cart. */
	private FragmentCart mFragmentCart;



	/**
	 * The listener interface for receiving incrementQuantity events.
	 * The class that is interested in processing a incrementQuantity
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addIncrementQuantityListener<code> method. When
	 * the incrementQuantity event occurs, that object's appropriate
	 * method is invoked.
	 *
	 */
	private class IncrementQuantityListener implements OnClickListener {

		/** The product id. */
		int productId;

		/**
		 * Instantiates a new increment quantity listener.
		 *
		 * @param productId the product id
		 */
		public IncrementQuantityListener(int productId) {
			super();
			this.productId = productId;
		}

		/* (non-Javadoc)
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View v) {
			CartManager.getInstance().incrementQuantityOfProduct(productId);

			notifyDataSetChanged();

			mFragmentCart.generateValues();
		}
	}

	/**
	 * The listener interface for receiving decrementQuantity events.
	 * The class that is interested in processing a decrementQuantity
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addDecrementQuantityListener<code> method. When
	 * the decrementQuantity event occurs, that object's appropriate
	 * method is invoked.
	 *
	 */
	private class DecrementQuantityListener implements OnClickListener {

		/** The product id. */
		int productId;

		/**
		 * Instantiates a new decrement quantity listener.
		 *
		 * @param productId the product id
		 */
		public DecrementQuantityListener(int productId) {
			super();
			this.productId = productId;
		}

		/* (non-Javadoc)
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View v) {
			CartManager.getInstance().decrementQuantityOfProduct(productId);

			notifyDataSetChanged();

			mFragmentCart.generateValues();
		}
	}
}
