package com.example.rest

import com.example.dto.CreatePersonDto
import com.example.dto.CreateRandomPeopleDto
import com.example.dto.UpdatePersonDto
import com.example.rest.jpa.PersonService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
@RequestMapping("/people")
class PersonController(val service: PersonService) {

    @GetMapping
    fun getAll() = service.getAllPeople()

    @PostMapping
    fun addPerson(@RequestBody dto: CreatePersonDto) = service.addPerson(dto)

    @PostMapping("/random")
    fun addRandomPeople(@RequestBody dto: CreateRandomPeopleDto) = service.addRandomPeople(dto)

    @PutMapping("/{id}")
    fun replacePerson(@PathVariable id: Int, @RequestBody dto: CreatePersonDto) {
        service.replacePerson(id, dto)
    }

    @PatchMapping("/{id}")
    fun updatePerson(@PathVariable id: Int, @RequestBody dto: UpdatePersonDto) {
        service.updatePerson(id, dto)
    }

    @DeleteMapping("/{id}")
    fun deletePerson(@PathVariable id: Int) {
        service.deletePerson(id)
    }
}
