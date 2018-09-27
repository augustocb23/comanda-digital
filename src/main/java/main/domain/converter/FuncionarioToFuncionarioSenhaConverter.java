package main.domain.converter;

import main.domain.Funcionario;
import main.domain.FuncionarioSenha;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioToFuncionarioSenhaConverter implements Converter<Funcionario, FuncionarioSenha> {
	@Override
	public FuncionarioSenha convert(Funcionario funcionario) {
		if (funcionario == null)
			return null;

		FuncionarioSenha fs = new FuncionarioSenha();
		fs.setCodigo(funcionario.getCodigo());
		fs.setAtivo(funcionario.getAtivo());
		fs.setLogin(funcionario.getLogin());
		fs.setNome(funcionario.getNome());
		fs.setSenha(funcionario.getSenha());
		fs.setGrupo(funcionario.getGrupo());
		return fs;
	}
}
