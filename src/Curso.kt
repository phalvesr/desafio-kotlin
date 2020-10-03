/*
*   Lista de métodos implementados:
*   - adicionarAluno        ✔️
*   - excluirAluno          ✔️
*   - matricularAluno       ✔️
* */

class Curso(
    var nomeCurso: String?,
    var codigoCurso: String?,
    var professorTitular: ProfessorTitular?,
    var professorAdjunto: ProfessorAdjunto?,
    var quantidadeMaximaAlunos: Int,
) : ICurso {

    val listaMatriculados: MutableSet<Aluno> = mutableSetOf()

    constructor(nomeCurso: String, codigoCurso: String, maximoAlunos: Int)
            : this(nomeCurso, codigoCurso, null, null, maximoAlunos)

    override fun adicionarAluno(novoAluno: Aluno?): Boolean {
        novoAluno?.let {
            if (this.listaMatriculados.contains(it)) return false
            if (this.quantidadeMaximaAlunos < this.quantidadeMaximaAlunos++) return false
            this.listaMatriculados.add(novoAluno)
            println("Aluno Matriculado")
            return true
        } ?: return false
    }

    fun mudarProfessores(professorTitular: ProfessorTitular, professorAdjunto: ProfessorAdjunto) {
        this.professorTitular = professorTitular
        this.professorAdjunto = professorAdjunto
    }

    override fun excluirAluno(aluno: Aluno?) {
        aluno?.let {
            this.listaMatriculados.remove(aluno)
            return
        } ?: return
    }
}