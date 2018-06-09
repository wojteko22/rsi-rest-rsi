import com.example.dto.CreatePersonDto
import com.example.dto.PersonDto
import com.example.dto.UpdatePersonDto
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.*


class Client {

    private val template = run {
        val requestFactory = HttpComponentsClientHttpRequestFactory()
        RestTemplate(requestFactory)
    }
    private val url = "http://localhost:8080/people"

    fun getAllPeople() {
        val people = template.getForObject<List<PersonDto>>(url)
        println(people)
    }

    fun addPerson(words: List<String>) {
        val name = words[1]
        val weight = words[2].toDouble()
        val person = CreatePersonDto(name, weight)
        val id = template.postForObject<Int>(url, person)
        println("id: $id")
    }

    fun replacePerson(words: List<String>) {
        val id = words[1].toInt()
        val name = words[2]
        val weight = words[3].toDouble()
        val person = CreatePersonDto(name, weight)
        tryToMakeSuccesfulRequest { template.put("$url/$id", person) }
    }

    fun updatePerson(words: List<String>) {
        val id = words[1].toInt()
        val name = tryToGetName(words)
        val weight = tryToGetWeight(words)
        val person = UpdatePersonDto(name, weight)
        tryToMakeSuccesfulRequest { template.patchForObject<Void>("$url/$id", person) }
    }

    fun deletePerson(words: List<String>) {
        val id = words[1].toInt()
        tryToMakeSuccesfulRequest { template.delete("$url/$id") }
    }

    private fun tryToMakeSuccesfulRequest(f: () -> Unit) {
        try {
            f()
        } catch (e: HttpClientErrorException) {
            println(e.responseBodyAsString)
        }
    }

    private fun tryToGetName(arguments: List<String>): String? {
        val word = arguments[2]
        return if (word == "-") null else word
    }

    private fun tryToGetWeight(arguments: List<String>): Double? =
        try {
            arguments[3].toDouble()
        } catch (e: Exception) {
            null
        }
}
