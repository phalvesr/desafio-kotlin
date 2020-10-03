class ProfessorTitular(
    override var nomeProfessor: String?,
    override var sobrenomeProfessor: String?,
    override var codigoProfessor: String?,
    var especialidade: String?,
) : Professor(nomeProfessor, sobrenomeProfessor, codigoProfessor) {
}