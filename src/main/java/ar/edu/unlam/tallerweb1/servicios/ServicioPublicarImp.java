package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Etiqueta;
import ar.edu.unlam.tallerweb1.modelo.Libro;
import ar.edu.unlam.tallerweb1.modelo.Publicacion;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioEtiqueta;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioLibro;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPublicacion;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Service("servicioPublicar")
@Transactional
public class ServicioPublicarImp implements ServicioPublicar {

    private RepositorioLibro servicioLibroDao;
    private RepositorioUsuario servicioUsuarioDao;
    private RepositorioPublicacion servicioPublicacionDao;
    private RepositorioEtiqueta servicioEtiquetaDao;

    @Autowired
    public ServicioPublicarImp(RepositorioLibro servicioLibroDao, RepositorioUsuario servicioUsuarioDao, RepositorioPublicacion servicioPublicacionDao, RepositorioEtiqueta servicioEtiquetaDao) {
        this.servicioUsuarioDao = servicioUsuarioDao;
        this.servicioLibroDao = servicioLibroDao;
        this.servicioPublicacionDao = servicioPublicacionDao;
        this.servicioEtiquetaDao = servicioEtiquetaDao;
    }

    @Override
    public void subirArchivo(String nombre, Double precio, MultipartFile archivo, MultipartFile imagen, String ruta, Usuario propietario, List<Etiqueta> etiquetas) throws IOException {
        Path rutaPdf = Paths.get(ruta + "/uploads/" + archivo.getOriginalFilename());
        Path rutaImg = Paths.get(ruta + "/img/" + imagen.getOriginalFilename());
        String path = "/uploads/" + archivo.getOriginalFilename();
        String pathImg = "/img/" + imagen.getOriginalFilename();
        Files.copy(archivo.getInputStream(), rutaPdf, StandardCopyOption.REPLACE_EXISTING);
        Files.copy(imagen.getInputStream(), rutaImg, StandardCopyOption.REPLACE_EXISTING);
        Libro libro = new Libro(nombre, path, pathImg);
        Publicacion publicacion = new Publicacion(libro, propietario, precio, etiquetas);
        servicioPublicacionDao.cargarPublicacion(publicacion);
    }

    @Override
    public List<Publicacion> listarPubliacion(Usuario user) {
        return servicioPublicacionDao.listarPublicaciones(user);
    }

    @Override
    public List<Etiqueta> listarEtiquetas() {
        return servicioEtiquetaDao.listarEtiquetas();
    }

    @Override
    public List<Etiqueta> parsearEtiquetas(String[] etiquetas) {
        return servicioEtiquetaDao.parsearEtiquetas(etiquetas);
    }

}
