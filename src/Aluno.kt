/*
*   Lista de métodos implementados:
*   - [override] equals (Ensinando a classe a comparar dois alunos)     ✔️
*
* */

class Aluno (
    override var nome: String?,
    override var sobrenome: String?,
    var codigoAluno: String?,
) : Pessoa(nome, sobrenome) {

    override fun equals(other: Any?): Boolean {
        (other as? Aluno)?.let {
            return (it.codigoAluno == this.codigoAluno || (this.nome == it.nome
                    && this.sobrenome == it.sobrenome))
        } ?: return false
    }

}