package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.repository.UsuarioRepository;
import ar.edu.unnoba.Proyecto.model.Usuario;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    @Transactional(readOnly = true) //Transactional permite que se realize las transacciones correctamente a la bd
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(usuario.getUsername(), usuario.getPassword(), List.of(new SimpleGrantedAuthority("ADMIN")));
    }//retorna un User (implementa UserDetails), donde el constructor tiene: username, password y lista de autoridades (roles o permisos).

    @Override
    @Transactional
    public void save(Usuario usuario) { //sirve para guardar y actualizar
        usuarioRepository.save(usuario);
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
    }

    @Override
    @Transactional(readOnly = true)//utilizar readOnly hace que se limite unicamente a leer, lo cual da seguridad y es mas eficiente
    public Usuario get(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    public long countUsuarios() {
        return usuarioRepository.count();
    }
}