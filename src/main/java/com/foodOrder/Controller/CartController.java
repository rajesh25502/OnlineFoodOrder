package com.foodOrder.Controller;

import com.foodOrder.Model.Cart;
import com.foodOrder.Model.CartItem;
import com.foodOrder.Request.AddCartItemRequest;
import com.foodOrder.Request.UpdateCartItemRequest;
import com.foodOrder.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PutMapping("/add")
    public ResponseEntity<CartItem> addCartItem(@RequestBody AddCartItemRequest request,
                                                @RequestHeader("Authorization") String jwt)throws Exception
    {
        CartItem cartItem=cartService.addItemToCart(request,jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateCartItemRequest request)throws Exception
    {
        CartItem cartItem=cartService.updateCartItemQuantity(request.getCartItemId(),request.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("-item/{id}/remove")
    public ResponseEntity<Cart> removeCartItem(@RequestHeader("Authorization") String jwt,
                                                           @PathVariable Long id)throws Exception
    {
        Cart cart=cartService.removeCartItem(id,jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt)throws Exception
    {
        Cart cart=cartService.clearCart(jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/findCart")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt)throws Exception
    {
        Cart cart=cartService.findUserCart(jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/{cartId}/findCart")
    public ResponseEntity<Cart> findCartById(@PathVariable Long cartId)throws Exception
    {
        Cart cart=cartService.findCartByID(cartId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

//    @GetMapping("/calculateTotal")
//    public ResponseEntity<Cart> calculateCartTotal(@RequestBody Cart req)throws Exception
//    {
//        Cart cart=cartService.calculateCartTotal(req);
//        return new ResponseEntity<>(cart, HttpStatus.OK);
//    }
}
