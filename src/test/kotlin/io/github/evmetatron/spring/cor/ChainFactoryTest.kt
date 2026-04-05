package io.github.evmetatron.spring.cor

import io.github.evmetatron.spring.cor.fixtures.ChainA
import io.github.evmetatron.spring.cor.fixtures.ChainB
import io.github.evmetatron.spring.cor.fixtures.ChainC
import io.github.evmetatron.spring.cor.fixtures.ChainInterface
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verifyOrder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationContext

class ChainFactoryTest {
    private lateinit var chainFactory: ChainFactory
    private lateinit var context: ApplicationContext

    @BeforeEach
    fun beforeEach() {
        context = mockk()
        chainFactory = ChainFactory(context)
    }

    @Test
    fun `create chain by classes and handle chain methods`() {
        val chainA = spyk(ChainA())
        val chainB = spyk(ChainB())
        val chainC = spyk(ChainC())

        mockBeans(
            listOf(chainA, chainB, chainC),
        )

        val chains = chainFactory.createChain(ChainInterface::class.java)

        chains shouldBe chainA

        chains.handle()

        verifyOrder {
            chainA.handle()
            chainC.handle()
            chainB.handle()
        }
    }

    @Test
    fun `create empty chain and handling method works without errors`() {
        mockBeans(emptyList())

        val actual = chainFactory.createChain(ChainInterface::class.java)

        actual.handle()
    }

    private fun mockBeans(beans: List<ChainInterface>) {
        every { context.getBeansOfType(ChainInterface::class.java).values } returns beans.toMutableList()
    }
}
