import java.time.LocalDate
import java.time.Period

open class Professor (
    open var nomeProfessor: String?,
    open var sobrenomeProfessor: String?,
    open var codigoProfessor: String?,
) : Pessoa(nomeProfessor, sobrenomeProfessor) {

    private var tempoInicio: LocalDate


    init {
        tempoInicio = LocalDate.now()
    }


    // Basicamente adaptei o que foi feito aqui:
    // https://javarevisited.blogspot.com/2016/10/how-to-get-number-of-months-and-years-between-two-dates-in-java.html
    fun getTempoCasa(): Int {
        val tempoAtual = LocalDate.now()
        return Period.between(tempoInicio, tempoAtual).months
    }
}