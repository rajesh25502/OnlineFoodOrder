package com.foodOrder.Service;

import com.foodOrder.Model.Cart;
import com.foodOrder.Model.CartItem;
import com.foodOrder.Model.User;
import com.foodOrder.Request.AddCartItemRequest;

public interface CartService {

    public CartItem addItemToCart(AddCartItemRequest req, String jwt)throws Exception;

    public CartItem updateCartItemQuantity(Long cartItemId,int quantity)throws Exception;

    public Cart removeCartItem(Long cartItemId,String jwt)throws Exception;

//    public Cart calculateCartTotal(Cart cart)throws Exception;

    public Cart findCartByUserID(Long userId) throws Exception;

    public Cart findCartByID(Long id)throws Exception;

    public Cart findUserCart(String jwt)throws Exception;

    public Cart clearCart(String jwt)throws Exception;
}
