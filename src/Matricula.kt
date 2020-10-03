import java.time.LocalDate

class Matricula(
    var aluno: Aluno,
    var curso: Curso,
) {

    lateinit var dataMatricula: LocalDate

    init {
        dataMatricula = LocalDate.now();
    }

}