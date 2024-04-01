package com.foodOrder.Service;

import com.foodOrder.DTO.RestaurantDTO;
import com.foodOrder.Model.Address;
import com.foodOrder.Model.Restaurant;
import com.foodOrder.Model.User;
import com.foodOrder.Repository.AddressRepo;
import com.foodOrder.Repository.RestaurantRepo;
import com.foodOrder.Repository.UserRepo;
import com.foodOrder.Request.CreateRestaurantRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    private RestaurantRepo restaurantRepo;
    @Autowired
    private AddressRepo addressRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address=addressRepo.save(req.getAddress());
        Restaurant restaurant=new Restaurant();
        restaurant.setAddress(req.getAddress());
        restaurant.setContactInfo(req.getContactInfo());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);

        return restaurantRepo.save(restaurant);

    }

    @Override
    public Restaurant updateRestaurant(Long restaurantID, CreateRestaurantRequest updatedReq) throws Exception {
        Restaurant restaurant=findRestaurantByID(restaurantID);
        if(restaurant!=null)
        {

            if(updatedReq.getName()!=null)
            {
                restaurant.setName(updatedReq.getName());
            }
            if(updatedReq.getDescription()!=null)
            {
                restaurant.setDescription(updatedReq.getDescription());
            }
            if(updatedReq.getCuisineType()!=null)
            {
                restaurant.setCuisineType(updatedReq.getCuisineType());
            }
            if(updatedReq.getContactInfo()!=null)
            {
                restaurant.setContactInfo(updatedReq.getContactInfo());
            }
            if(updatedReq.getOpeningHours()!=null)
            {
                restaurant.setOpeningHours(updatedReq.getOpeningHours());
            }
            if(updatedReq.getImages()!=null)
            {
                restaurant.setImages(updatedReq.getImages());
            }

        }
        else
        {
            throw new Exception("Restaurant not found...");
        }
        return restaurantRepo.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantID) throws Exception {
        Restaurant restaurant=findRestaurantByID(restaurantID);
        restaurantRepo.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepo.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepo.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantByID(Long ID) throws Exception {
        Optional<Restaurant> opt=restaurantRepo.findById(ID);
        if(opt.isEmpty())
        {
            throw new Exception("Restaurant not found with ID "+ID);
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserID(Long userID) throws Exception {
        Restaurant restaurant=restaurantRepo.findByOwnerId(userID);
        if(restaurant==null)
        {
            throw new Exception("Restaurant not found with userID "+userID);
        }
        return restaurant;
    }

    @Override
    public RestaurantDTO addToFavorite(Long restaurantID, User user) throws Exception {
        Restaurant restaurant=findRestaurantByID(restaurantID);
        RestaurantDTO dto=new RestaurantDTO();
        dto.setDescription(restaurant.getDescription());
        dto.setName(restaurant.getName());
        dto.setImages(restaurant.getImages());
        dto.setId(restaurantID);


        boolean isFavorite=false;
        List<RestaurantDTO> favorites=user.getFavorite();
        for(RestaurantDTO fav:favorites)
        {
            if(restaurantID.equals(fav.getId()))
            {
                isFavorite=true;
                favorites.remove(dto);
                break;
            }
        }
        if(!isFavorite)
        {
            favorites.add(dto);
        }

        userRepo.save(user);
        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long ID) throws Exception {
        Restaurant restaurant=findRestaurantByID(ID);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepo.save(restaurant);
    }
}
