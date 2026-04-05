package io.github.evmetatron.spring.cor.fixtures

import io.github.evmetatron.spring.cor.ChainNext
import org.springframework.core.annotation.Order

interface ChainInterface {
    fun handle()
}

@Order(1)
class ChainA : ChainInterface {
    @ChainNext
    private lateinit var next: ChainInterface

    override fun handle() {
        next.handle()
    }
}

@Order(3)
class ChainB : ChainInterface {
    @ChainNext
    private lateinit var next: ChainInterface

    override fun handle() {
        next.handle()
    }
}

@Order(2)
class ChainC : ChainInterface {
    @ChainNext
    private lateinit var next: ChainInterface

    override fun handle() {
        next.handle()
    }
}
