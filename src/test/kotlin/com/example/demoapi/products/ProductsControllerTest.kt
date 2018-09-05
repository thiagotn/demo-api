package com.example.demoapi.products

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@RunWith(SpringJUnit4ClassRunner::class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@WebMvcTest(controllers = [ProductsController::class])
class ProductsControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @get:Rule
    var restDocumentation = JUnitRestDocumentation()

    @Autowired
    lateinit var context: WebApplicationContext

    @MockBean
    private lateinit var service: ProductsService

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(this.restDocumentation)
                        .uris()
                        .withScheme("http")
                        .withHost("localhost")
                        .withPort(8080))
                .build()
    }

    @Test
    fun shouldCreateProduct() {
        val id = 1
        val product = Product(id = id, name = "Book Kotlin for Android Developers - ${id}st Edition", description = "Kotlin for Android Developers - ${id}st Edition: Learn Kotlin the easy way while developing an Android App")
        `when`(service.create(product)).thenReturn(product)
        mockMvc.perform(post("/products")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(product)))
                .andDo(print())
                .andDo(document("{ClassName}/{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").description("Product Identifier"),
                                fieldWithPath("name").description("Product Name"),
                                fieldWithPath("description").description("Product Description")
                        )))
                .andExpect(status().isCreated)

    }

    private fun json(any: Any): String = mapper.writeValueAsString(any)
}