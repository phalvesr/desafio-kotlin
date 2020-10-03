// Nesta classe considerarei que o usuário pode usar algum tipo de serviço para enviar dados
// e que estes dados podem ser nulos por algum motivo (por isso usei variaveis "nullaveis").

/* Lista de métodos implementados:
*  - registrarCurso                     ✔️
 * - registrarProfessorAdjunto          ✔ ️
 * - registrarAluno                     ✔   ️
 * - registrarProfessorTitular          ✔️
 * - matricularAluno                    ✔️
 * - excluirProfessor                   ✔️
 * - excluirCurso                       ✔ ️
 * - alocarProfessores                  ✔
 * - listarCursos                       ✔
 * - listarProfessores                  ✔
 * - listarAlunos                       ✔
 * - [private] jaEAluno                 ✔️
 * - [private] validarCursoPorCodigo    ✔️
 * - [private] validarAlunoPorCodigo    ✔
*/

class DigitalHouseManager {
    private val listaAlunos = mutableSetOf<Aluno>()
    private val listaProfessores = mutableSetOf<Professor>()
    private val listaMatricula = mutableSetOf<Matricula>()
    private val listaCurso = mutableSetOf<Curso>()

    fun registrarCurso(nomeCurso: String, codigoCurso: String, maximoAlunos: Int) {
        try {
            // Considerando que um manager pode ter sobre seu controle somente um tipo de curso
            // e garantindo um código exclusivo p/ cada curso
            if (this.listaCurso.any { it.nomeCurso == nomeCurso || it.codigoCurso == codigoCurso }) {
                throw IllegalArgumentException("Falha ao criar curso. Curso e/ou código já existentes.")
            }
            val novoCurso = Curso(nomeCurso, codigoCurso, maximoAlunos)
            this.listaCurso.add(novoCurso)
            println("Curso adicionado com sucesso!")
        } catch (exception: IllegalArgumentException) {
            println(exception.stackTraceToString())
        }
    }

    fun registrarProfessorAdjunto(nome: String?, sobrenome: String?, codigoProfessor: String?, quantidadeHoras: Int?) {
        try {
            if (nome == null || sobrenome == null || codigoProfessor == null || quantidadeHoras == null)
                throw IllegalArgumentException("Parâmetros nulos não são aceitos para um professor.")
            val novoProfessor = ProfessorAdjunto(nome, sobrenome, codigoProfessor, quantidadeHoras)
            if (this.listaProfessores.any { it == novoProfessor }) {
                println("Professor já consta no cadastro.")
                return
            }
            this.listaProfessores.add(novoProfessor)
        } catch (exception: IllegalArgumentException) {
            println(exception.stackTraceToString())
        }
    }

    fun registrarAluno(nome: String?, sobrenome: String?, codigoAluno: String?) {
        try {
            when {
                (nome == null || sobrenome == null || codigoAluno == null) -> throw IllegalArgumentException("Dados do aluno são inválidos")
                (jaEAluno(nome, sobrenome, codigoAluno)) -> {
                    println("Aluno já é cadastrado")
                    return
                }
                else -> {
                    val novoAluno = Aluno(nome, sobrenome, codigoAluno)
                    this.listaAlunos.add(novoAluno)
                    return
                }
            }
        } catch (exception: IllegalArgumentException) {
            println(exception.stackTraceToString())
        }
    }

    fun registrarProfessorTitular(nome: String?, sobrenome: String?, codigoProfessor: String?, especialidade: String?) {
        try {
            if (nome == null || sobrenome == null || codigoProfessor == null || especialidade == null)
                throw IllegalArgumentException("Verifique os dados do novo professor titular e tente novamente.")
            val novoProfessorTitular = ProfessorTitular(nome, sobrenome, codigoProfessor, especialidade)

            if (!(this.listaProfessores.any { it == novoProfessorTitular })) {
                this.listaProfessores.add(novoProfessorTitular)
            }
        } catch (exception: IllegalArgumentException) {
            println(exception.stackTraceToString())
        }
    }

    fun matricularAluno(codigoAluno: String?, codigoCurso: String?) {
        try {
            when {
                (codigoAluno == null || codigoCurso == null) -> throw IllegalArgumentException("Verifique os dados da matricula do aluno e tente novamente.")
                (!validarCursoPorCodigo(codigoCurso)) -> throw  IllegalArgumentException("O código do curso indicado não é válido.")
                (!validarAlunoPorCodigo(codigoAluno)) -> throw  IllegalArgumentException("O código do aluno indicado não é válido.")
            }

            for (curso in this.listaCurso) {
                if (curso.codigoCurso == codigoCurso) {

                    val aluno = this.listaAlunos.find { it.codigoAluno == codigoAluno }
                    val alunoMatriculado = curso.adicionarAluno(aluno)

                    if (!alunoMatriculado)
                        println("Não foi possível realizar a matrícula porque não há vagas.")
                    break
                }
            }


        } catch (exception: IllegalArgumentException) {
            println(exception.stackTraceToString())
        }
    }

    fun excluirProfessor(codigoProfessor: String?) {
        codigoProfessor?.let { codigoProfessor ->
            try {
                if (this.listaProfessores.removeIf { it.codigoProfessor == codigoProfessor }) {
                    println("Professor excluido")
                    return
                }
                throw IllegalArgumentException("Código $codigoProfessor não corresponde a um professor cadastrado.")
            } catch (exception: IllegalArgumentException) {
                println(exception.stackTraceToString())
            }
        } ?: println("Código não reconhecido pelo sistema.")
    }

    fun excluirCurso(codigoCurso: String?) {
        codigoCurso?.let { codigoCurso ->
            try {
                if (this.listaCurso.removeIf { it.codigoCurso == codigoCurso }) {
                    println("Curso excluído com sucesso!")
                    return
                }
                throw IllegalArgumentException("Curso com código $codigoCurso não encontrado.")
            } catch (exception: IllegalArgumentException) {
                println(exception.stackTraceToString())
            }
        } ?: println("Código não reconhecido pelo sistema.")
    }

    fun alocarProfesssores(codigoCurso: String?, codigoProfessorTitular: String?, codigoProfessorAdjunto: String?) {
        try {
            if (codigoCurso == null || codigoProfessorTitular == null || codigoProfessorAdjunto == null)
                throw IllegalArgumentException("Os parametros para alocar um professor devem ser válidos")

            val titularValido = this.listaProfessores.any { it.codigoProfessor == codigoProfessorTitular }
            val adjuntoValido = this.listaProfessores.any { it.codigoProfessor == codigoProfessorAdjunto }
            val cursoValido = this.listaCurso.any { it.codigoCurso == codigoCurso }

            when {
                !titularValido -> throw IllegalArgumentException("Este professor titular não existe na lista de professores.")
                !adjuntoValido -> throw IllegalArgumentException("Este professor adjunto não existe na lista de professores.")
                !cursoValido -> throw IllegalArgumentException("Este curso não existe na lista de cursos")
            }

            // Nestas linhas temos certeza que os elementos não podem ser nulos portanto o safe cast (as?) não é necessário!!
            val titularAlocado =
                ((this.listaProfessores.find { it.codigoProfessor == codigoProfessorTitular }) as ProfessorTitular)
            val adjuntoAlocado =
                ((this.listaProfessores.find { it.codigoProfessor == codigoProfessorAdjunto } as ProfessorAdjunto))

            for (curso in this.listaCurso) {
                if (curso.codigoCurso == codigoCurso) {
                    curso.mudarProfessores(titularAlocado, adjuntoAlocado)
                    break
                }
            }
            println("Professores alocados")
        } catch (exception: IllegalArgumentException) {
            println(exception.stackTraceToString())
        }
    }

    fun listarCursos() {
        if (this.listaCurso.size == 0) {
            println("Este gerente não tem cursos cadastrados.")
        }
        this.listaCurso.forEach {
            println("Disciplina: ${it.nomeCurso}," +
                    " professor titular: ${it.professorTitular?.nomeProfessor ?: "Sem professor titular"}, " +
                    "professor adjunto: ${it.professorAdjunto?.nomeProfessor ?: "Sem professor adjunto"}.")
        }
    }

    fun listarProfessores() {
        if (this.listaProfessores.size < 1) {
            println("Este gerente não tem professores cadastrados.")
            return
        }
        this.listaProfessores.forEach {
            println("Professor: ${it.nomeProfessor} ${it.sobrenomeProfessor}, código professor: ${it.codigoProfessor}")
        }
    }

    fun listarAlunos() {
        if (this.listaAlunos.size < 1) {
            println("Este gerente não tem alunos matriculados em cursos")
            return
        }
        this.listaAlunos.forEach {
            println("Nome aluno: ${it.nomeAluno}, código aluno: ${it.codigoAluno}.")
        }
    }

    private fun jaEAluno(nomeAluno: String, sobrenomeAluno: String, codigoAluno: String): Boolean {
        this.listaAlunos.forEach {
            if (it.nomeAluno == nomeAluno && sobrenomeAluno == it.sobrenomeAluno && it.codigoAluno == codigoAluno) {
                return true
            }
        }
        return false
    }

    private fun validarCursoPorCodigo(codigoCurso: String?): Boolean {
        codigoCurso?.let {
            return this.listaCurso.any { it.codigoCurso == codigoCurso }
        } ?: return false
    }

    private fun validarAlunoPorCodigo(codigoAluno: String?): Boolean {
        codigoAluno?.let {
            return this.listaAlunos.any { it.codigoAluno == codigoAluno }
        } ?: return false
    }
}
