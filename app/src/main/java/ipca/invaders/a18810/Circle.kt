package ipca.invaders.a18810

import java.util.*
import android.util.Log

class Circle {

    //ecrã
    var x = 0f
    var y = 0f
    var speedX = 0
    var speedY = 0
    var raio = 50f

    //objeto em si
    var maxY = 0
    var minY = 0f
    var maxX = 0
    var minX = 0f

    //private var _isGoingDown = false

    //var isGoingDown : Boolean
    //    get() = _isGoingDown
    //    set(value) {
    //
    //        //Só atribui o valor bool quando o y = 0 ou seja quando toca na parte de cima
    //        if (y == minY) {
    //            _isGoingDown = value
    //        }
    //    }

    constructor(width: Int, height: Int, posX: Float, posY: Float){

        //ecrã
        maxX = width
        minX = 0f
        maxY = height
        minY = 0f




        val generator =  Random()

        x = posX
        y = posY

        //Componente x e y aleatoria
        speedX = generator.nextInt(5 + 5) - 5
        speedY = generator.nextInt(5 + 5) - 5

    }

    fun update(){

        x += speedX
        y += speedY


        if (x - raio <= minX || x + raio >= maxX.toFloat())
        {
            speedX = -speedX

        }

        if (y - raio <= minY.toFloat() || y + raio >= maxY.toFloat())
        {
            speedY = -speedY
        }


    }

}