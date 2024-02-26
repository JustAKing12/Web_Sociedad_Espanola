package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.repository.UsuarioRepository;
import ar.edu.unnoba.Proyecto.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(new BCryptPasswordEncoder().encode("123")); //NO BORRAR
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> authorities = usuario.getAuthorities().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());

        return new User(usuario.getUsername(), usuario.getPassword(), authorities);
    }//retorna un User (implementa UserDetails), donde el constructor tiene: username, password y lista de autoridades (roles o permisos).

    @Override
    @Transactional
    public void save(Usuario usuario) { //sirve para guardar y actualizar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
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

    @Override
    public long countUsuarios() {
        return usuarioRepository.count();
    }

    @Override
    public Usuario buscarPorNombre(String nombreUsuario) {
        return usuarioRepository.findByUsername(nombreUsuario);
    }
}