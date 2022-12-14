//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 10/22
// * Definition: Implementation of GameController class.
// * Note: <Info>
//**********************************************************************************************************************
package com.example.jupiter.controller;

//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Project includes
import com.example.jupiter.service.GameService;
import com.example.jupiter.service.TwitchException;

// Framework includes
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

// System includes
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@Controller
public class GameController {

//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************
    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public void GetGameFromService(@RequestParam(value = "game_name", required = false) String gameName,
                                   HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            // Return the dedicated game information if gameName is provided in the request URL, otherwise return the top x games.
            if (gameName != null) {
                response.getWriter().print(new ObjectMapper().writeValueAsString(gameService.SearchGame(gameName)));
            } else {
                response.getWriter().print(new ObjectMapper().writeValueAsString(gameService.GetTopGames(10)));
            }
        } catch (TwitchException e) {
            throw new ServletException(e);
        }
    }

//**********************************************************************************************************************
// * Private attributes
//**********************************************************************************************************************
    @Autowired
    private GameService gameService;
}
