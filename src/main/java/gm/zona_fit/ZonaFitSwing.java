package gm.zona_fit;

import com.formdev.flatlaf.FlatDarculaLaf;
import gm.zona_fit.gui.ZonaFitForma;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
@SpringBootApplication
public class ZonaFitSwing {
    public static void main(String[] args) {
        //Configurar modo oscuro
        FlatDarculaLaf.setup();
        //Instanciar la fabrica ded Spring
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(ZonaFitSwing.class)
                        .headless(false)
                        .web(WebApplicationType.NONE)
                        .run(args);
        //Crar un objeto Swing
        //Invocamos swing de maner indirecta, espera que todo los componentes esten levantados.
        SwingUtilities.invokeLater(()->{
            ZonaFitForma zona = context.getBean(ZonaFitForma.class);
           // ZonaFitForma forma = new ZonaFitForma(); reemplaza a esto ya que era importante usar el @Autowired, y esto no lo iba a realizar
            zona.setVisible(true);
        });
    }
}
