//package br.com.fiap.motoflow.controller.web;
//
//import br.com.fiap.motoflow.dto.web.MotoDtoWeb;
//import br.com.fiap.motoflow.service.MotoService;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.stream.Collectors;
//
//@Controller
//@RequestMapping("/web/motos")
//public class MotoWebController {
//
//    private final MotoService motoService;
//
//    public MotoWebController(MotoService motoService) {
//        this.motoService = motoService;
//    }
//
//    @GetMapping("/{id}")
//    public String index(@PathVariable Long id, Model model, Pageable pageable) {
//        var motosPage = motoService.findAllByPatioId(id, pageable);
//
//        var motoDtos = motosPage.getContent().stream().map(moto -> {
//            String posicaoPatio = "-";
//            if (moto.getPosicaoPatio() != null) {
//                posicaoPatio = moto.getPosicaoPatio().getPosicaoHorizontal() + moto.getPosicaoPatio().getPosicaoVertical();
//            }
//            return new MotoDtoWeb(
//                    moto.getId(),
//                    moto.getTipoMoto(),
//                    moto.getAno(),
//                    moto.getPlaca(),
//                    moto.getPrecoAluguel(),
//                    moto.getStatusMoto(),
//                    posicaoPatio
//            );
//        }).collect(Collectors.toList());
//
//        var motoDtosPage = new PageImpl<>(motoDtos, motosPage.getPageable(), motosPage.getTotalElements());
//
//        model.addAttribute("motos", motoDtosPage);
//        model.addAttribute("patioId", id);
//        return "motos";
//    }
//}
//
