package tn.esprit.skillexchange.Service.GestionProduit;


import tn.esprit.skillexchange.Entity.GestionProduit.CartProduct;

import java.util.List;

public interface ICartProductService {
    public List<CartProduct> retrieveCartProducts();
    public CartProduct retrieveCartProductById(Long cartpId);
  public CartProduct addProductToCart(Long cartId, Long productId, int quantity);/*addCartProduct(CartProduct cp);*/
    public void removeCartProduct(Long cartpId);
    public void clearCart(Long cartId);
    public CartProduct modifyCartProduct(Long cartPId, int newQuantity);
    public List<CartProduct> getProductsInCart(Long cartId);
}
