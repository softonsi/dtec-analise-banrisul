package br.com.softon.dtec.entity;


public interface Perfil {

    public enum TipoIdentificadorPerfil {
        CONTA_AGENCIA_TERMINAL(1), 
        CONTA_ESTABELECIMENTO_TERMINAL(2), 
        CONTA_DESTINO(3), 
        CONTA_CEDENTE(4),
        CEDENTE(5), 
        ESTABELECIMENTO(6), 
        CONTA_TIPO_TRANSACAO(7);

        private final int tipo;

        TipoIdentificadorPerfil(int tipo) {
            this.tipo = tipo;
        }

        public int toInt() {
            return tipo;
        }

        public short toShort() {
            return Short.valueOf(String.valueOf(tipo));
        }
    }

	Short getCodigoIdentificadorPerfil();
	
}