package com.AntonioP.YonkouTattoo.controller;

import com.AntonioP.YonkouTattoo.service.TatuajeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TatuajeController {
    private final TatuajeService tatuajeService;

    public TatuajeController(TatuajeService tatuajeService) {
        this.tatuajeService = tatuajeService;
    }

    @GetMapping("/tatuajes")
    public String gestionarTatuajes(Model model){
        model.addAttribute("tatuajes", tatuajeService.listarTatuajes());
        return "/tatuaje/tatuajes";
    }
}
