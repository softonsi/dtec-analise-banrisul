package br.com.softon.dtec.entity;

import java.sql.Timestamp;

public class PerfilInformacaoCliente extends PerfilInformacao {

	public PerfilInformacaoCliente(String cdDocIdentfClie, String cdTpIdentfClie) {
		super(null, cdDocIdentfClie, null, cdTpIdentfClie, null,
				null, null, null, null, null);
	}
	
	public PerfilInformacaoCliente(Short codIdentificacaoPerfil, String codVariavelPrimeira, String codVariavelQuarta,
			String codVariavelSegunda, String codVariavelTerceira, Timestamp dataPerfil, Integer qtdTotal,
			Double valorDesvioDiario, Double valorMedioDiario, Double valorTotal) {
		super(codIdentificacaoPerfil, codVariavelPrimeira, codVariavelQuarta, codVariavelSegunda, codVariavelTerceira,
				dataPerfil, qtdTotal, valorDesvioDiario, valorMedioDiario, valorTotal);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getCodVariavelPrimeira() == null) ? 0 : getCodVariavelPrimeira().hashCode());
		result = prime * result + ((getCodVariavelSegunda() == null) ? 0 : getCodVariavelSegunda().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PerfilInformacao other = (PerfilInformacao) obj;
		if (getCodVariavelPrimeira() == null) {
			if (other.getCodVariavelPrimeira() != null)
				return false;
		} else if (!getCodVariavelPrimeira().equals(other.getCodVariavelPrimeira()))
			return false;
		if (getCodVariavelSegunda() == null) {
			if (other.getCodVariavelSegunda() != null)
				return false;
		} else if (!getCodVariavelSegunda().equals(other.getCodVariavelSegunda()))
			return false;
		return true;
	}
}
