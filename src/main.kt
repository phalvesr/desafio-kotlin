fun main() {
    val gerente = DigitalHouseManager()
    val prof = ProfessorTitular("John", "Doe", "1", "C")
    val prof2 = ProfessorAdjunto("Doe", "John", "2", 10)

    gerente.registrarProfessorTitular("John", "Doe", "1", "C")
    gerente.registrarProfessorAdjunto("Doe", "John", "2", 10)
    gerente.registrarCurso("Mat", "12", 3)
    gerente.alocarProfesssores("12", "1", "2")
    gerente.registrarAluno("Navi", "Thomas", "3")
    gerente.registrarAluno("Vitor", "Lima", "4")
    gerente.listarCursos()
    gerente.excluirCurso("12")
    gerente.excluirProfessor("1")
    gerente.listarCursos()
    gerente.listarProfessores()
    gerente.listarAlunos()
    println(prof.getTempoCasa())
}