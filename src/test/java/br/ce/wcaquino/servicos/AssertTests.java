package br.ce.wcaquino.servicos;

import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.utils.DataUtils;

public class AssertTests {

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	// Coloque os valores nos locais corretos
//	@Test
//	public void test() {
//
//		
//		Assert.assertTrue(true);
//		Assert.assertFalse(false);
//		
//		Assert.assertEquals(1,  1);
//		Assert.assertEquals(0.51234, 0.512, 0.001);
//		Assert.assertEquals(Math.PI, 3.14, 0.01);
//		
//		int i = 5;
//		Integer i2 = 5;
//		Assert.assertEquals(Integer.valueOf(i), i2);
//		Assert.assertEquals(i, i2.intValue());
//		Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
//		Usuario u1 = new Usuario("Usuario 1");
//		Usuario u2 = new Usuario("Usuario 1");
//		Usuario u3 = null;
//		LocacaoService service = new LocacaoService();
//		Filme filme = new Filme("Filme 1", 2, 5.0);
//		Locacao locacao = service.alugarFilme(u1, filme);
//		
//		Assert.assertEquals(u1,  u2);
//		
//		Assert.assertSame(u2, u2);
//		Assert.assertNull(u3);
//		
//		Assert.assertThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(5.0)));
//		Assert.assertThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.not(6.0)));
//
//	}

	// Se o teste não está esperando exceptions, deixe que o junit gerencie para
	// você.
	@Test
	public void testeAlocacao() throws Exception {
		// Cenario
		Usuario u1 = new Usuario("Usuario 1");
		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("Filme 1", 2, 5.0);

		// Acao
		Locacao locacao;

		locacao = service.alugarFilme(u1, filme);

		error.checkThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(5.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), DataUtils.obterDataComDiferencaDias(2)),
				CoreMatchers.is(false));

	}

	@Test(expected = FilmeSemEstoqueException.class)
	public void testeLocacao_filmeSemEstoque() throws Exception {
		// Cenario
		Usuario u1 = new Usuario("Usuario 1");
		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// Acao
		Locacao locacao;

		locacao = service.alugarFilme(u1, filme);

		error.checkThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(5.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), DataUtils.obterDataComDiferencaDias(2)),
				CoreMatchers.is(false));

	}

	@Test()
	public void testeLocacao_filmeSemEstoque_2() {
		// Cenario
		Usuario u1 = new Usuario("Usuario 1");
		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// Acao
		Locacao locacao;

		try {
			locacao = service.alugarFilme(u1, filme);
			Assert.fail("Deveria ter lançado excecao");
		} catch (Exception e) {
			Assert.assertThat(e.getMessage(), CoreMatchers.is("Filme sem estoque"));

		}

	}

	@Test
	public void testeLocacao_filmeSemEstoque_3() throws Exception {
		// Cenario
		Usuario u1 = new Usuario("Usuario 1");
		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("Filme 1", 0, 5.0);

		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");
		
		// Acao
		Locacao locacao = service.alugarFilme(u1, filme);


	}

}
