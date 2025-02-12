package design_pattern.abstract_factory

sealed interface Shape {
    fun draw()
}

interface ShapeFactory {
    fun create(): Shape
}


 class Circle: Shape {
    override fun draw() {
        println("Circle::draw()")
    }

     companion object Factory: ShapeFactory {
         override fun create() = Circle()
     }
}

 class Rectangle: Shape {
    override fun draw() {
        println("Rectangle::draw()")
    }

     companion object Factory: ShapeFactory {
         override fun create() = Rectangle()
     }
}

 class Square: Shape {
    override fun draw() {
        println("Square::draw()")
    }

     companion object Factory: ShapeFactory {
         override fun create() = Square()
     }
}
