
/*
*   Lista de métodos implementados:
*   - [override] equals (Ensinando a classe a comparar dois alunos)     ✔️
*
* */
class ProfessorAdjunto(

    override var nomeProfessor: String?,
    override var sobrenomeProfessor: String?,
    override var codigoProfessor: String?,
    var horasMonitoria: Int?,

    ) : Professor(nomeProfessor, sobrenomeProfessor, codigoProfessor) {

    override fun equals(other: Any?): Boolean {
        (other as? ProfessorAdjunto)?.let {
            return (it.nomeProfessor == this.nomeProfessor && it.sobrenomeProfessor == this.sobrenomeProfessor
                    && it.codigoProfessor == this.codigoProfessor)
        } ?: return false
    }
}