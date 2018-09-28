package main.security;

import main.persistence.service.FuncionarioUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final FuncionarioUserDetailsService funcionarioUserDetailsService;

	@Autowired
	public SecurityConfig(FuncionarioUserDetailsService funcionarioUserDetailsService) {
		this.funcionarioUserDetailsService = funcionarioUserDetailsService;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(funcionarioUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//controla o acesso à página
		http.authorizeRequests()
				//bloqueia o acesso à página de funcionários
				.and().authorizeRequests().antMatchers("/funcionarios/**").hasAuthority("editar_usuarios")
				//bloqueia o acesso à página de admin
				.and().authorizeRequests().antMatchers("/funcionarios/admin").hasAuthority("editar_admin")
				//libera o acesso à pasta CSS e JS
				.antMatchers("/css/**", "/js/**").permitAll()
				//permite o acesso para a página de comandas
				.antMatchers("/comandas").permitAll()
				//só autoriza o acesso se estiver logado
				.anyRequest().authenticated()

				//redireciona para a página de login
				.and().formLogin().loginPage("/login").permitAll()
				//após o login
				.successHandler(new CustomAuthenticationSuccessHandler())
				//url para logout
				.and().logout().logoutSuccessUrl("/login");
	}
}


