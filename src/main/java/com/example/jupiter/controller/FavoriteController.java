//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 10/22
// * Definition: Implementation of FavoriteController class.
// * Note: <Info>
//**********************************************************************************************************************
package com.example.jupiter.controller;

//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Project includes
import com.example.jupiter.entity.db.Item;
import com.example.jupiter.entity.request.FavoriteRequestBody;
import com.example.jupiter.service.FavoriteService;

// Framework includes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

// System includes
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@Controller
public class FavoriteController {

//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************

    @RequestMapping(value = "/favorite", method = RequestMethod.POST)
    public void setFavoriteItem(@RequestBody FavoriteRequestBody requestBody, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return ;
        }
        String userId = (String) session.getAttribute("user_id");
        favoriteService.setFavoriteItem(userId, requestBody.getFavoriteItem());
    }

    @RequestMapping(value = "/favorite", method = RequestMethod.DELETE)
    public void unsetFavoriteItem(@RequestBody FavoriteRequestBody requestBody, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        String userId = (String) session.getAttribute("user_id");
        favoriteService.unsetFavoriteItem(userId, requestBody.getFavoriteItem().getId());
    }

    @RequestMapping(value = "/favorite", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<Item>> getFavoriteItem(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new HashMap<>();
        }
        String userId = (String) session.getAttribute("user_id");
        return favoriteService.getFavoriteItems(userId);
    }

//**********************************************************************************************************************
// * Private Attributes
//**********************************************************************************************************************

    @Autowired
    private FavoriteService favoriteService;
}

