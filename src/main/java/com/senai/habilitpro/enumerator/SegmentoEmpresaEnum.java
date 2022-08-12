package com.senai.habilitpro.enumerator;

public enum SegmentoEmpresaEnum {
    ALIMENTOS_E_BEBIDAS("Alimentos e Bebidas"),
    CELULOSE_E_PAPEL("Celulose e Papel"),
    CONSTRUCAO("Construção"),
    EQUIPAMENTOS_ELETRICOS("Equipamentos Elétricos"),
    FARMACOS_E_EQUIPMAMENTOS_DE_SAUDE("Farmacos e Equipamentos de Saúde"),
    FUMO("Fumo"),
    INDUSTRIA_AUTOMOTICA("Indústria Automotiva"),
    INDUSTRIA_CERAMICA("Indústria Cerâmica"),
    INDUSTRIA_DIVERSA("Indústria Diversa"),
    INDUSTRIA_EXTRATIVA("Indústria Extrativa"),
    INDUSTRIA_GRAFICA("Indústria Gráfica"),
    MADEIRA_E_MOVEIS("Madeira e Móveis"),
    MAQUINAS_E_EQUIPAMENTOS("Maquinas e Equipamentos"),
    METALMECANICA_E_METALURGIA("Metalmecânica e Metalurgia"),
    OLEO_GAS_E_ELETRECIDADE("Oleo, Gas e Eletrecidade"),
    PRODUTOS_QUIMICOS_E_PLASTICOS("Produtos Químicos e Plásticos"),
    SANEAMENTO_BASICO("Saneamento Básico"),
    TIC("TIC"),
    TEXTIL_CONFECCAO_COURO_E_CALCADOS("Textil, Confeção, Couro e Calçados");

    private String descricao;

    SegmentoEmpresaEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
