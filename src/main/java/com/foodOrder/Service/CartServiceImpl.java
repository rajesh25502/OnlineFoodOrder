package com.foodOrder.Service;

import com.foodOrder.Model.Cart;
import com.foodOrder.Model.CartItem;
import com.foodOrder.Model.Food;
import com.foodOrder.Model.User;
import com.foodOrder.Repository.CartItemRepo;
import com.foodOrder.Repository.CartRepo;
import com.foodOrder.Request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private UserService userService;

    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Food food=foodService.findFoodByID(req.getFoodId());
        Cart cart=cartRepo.findByCustomerId(user.getId());

        for(CartItem cartItem: cart.getCartItem())
        {
            if(cartItem.getFood().equals(food))
            {
                int newQuantity=cartItem.getQuantity()+req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(),newQuantity);
            }
        }
        CartItem newCartItem=new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setIngredients(req.getIngredients());
        newCartItem.setTotalPrice(req.getQuantity()*food.getPrice());

        CartItem savedCartItem=cartItemRepo.save(newCartItem);
        cart.getCartItem().add(savedCartItem);
        cart.setTotal(cart.getTotal()+ newCartItem.getTotalPrice());
        cartRepo.save(cart);
        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItem=cartItemRepo.findById(cartItemId);
        if(cartItem.isEmpty())
        {
            throw new Exception("Cart item not found");
        }
        CartItem item=cartItem.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice()*quantity);
        return cartItemRepo.save(item);
    }

    @Override
    public Cart removeCartItem(Long cartItemId,String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Cart cart=cartRepo.findByCustomerId(user.getId());
        Optional<CartItem> cartItem=cartItemRepo.findById(cartItemId);
        if(cartItem.isEmpty())
        {
            throw new Exception("Cart item not found");
        }
        CartItem item=cartItem.get();
        cart.getCartItem().remove(item);
        cart.setTotal(cart.getTotal()-item.getTotalPrice());
        return cartRepo.save(cart);
    }

//    @Override
//    public Cart calculateCartTotal(Cart cart) throws Exception {
//        Double total= 0.0;
//        for(CartItem cartItem:cart.getCartItem())
//        {
//            total+=cartItem.getTotalPrice();
//        }
//        Cart savedCart=findCartByID(cart.getId());
//        savedCart.setTotal(total);
//        return cartRepo.save(savedCart);
//    }

    @Override
    public Cart findCartByID(Long id) throws Exception {
        Optional<Cart> cart=cartRepo.findById(id);
        if(cart.isEmpty())
        {
            throw new Exception("Cart not found");
        }
        return cart.get();
    }
    @Override
    public Cart findCartByUserID(Long userId) throws Exception {
        Optional<Cart> cart= Optional.ofNullable(cartRepo.findByCustomerId(userId));
        if(cart.isEmpty())
        {
            throw new Exception("Cart with userID not found");
        }
        return cart.get();
    }

    @Override
    public Cart findUserCart(String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        return findCartByUserID(user.getId());
    }

    @Override
    public Cart clearCart(String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Cart cart=findCartByUserID(user.getId());
        cart.getCartItem().clear();
        cart.setTotal(0.0);
        return cartRepo.save(cart);
    }
}
