package ipca.invaders.a18810

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.annotation.RequiresApi
import kotlin.math.pow
import kotlin.math.sqrt

class GameView : SurfaceView,Runnable {

    var playing = false
    var gameThread : Thread? = null

    var surfaceHolder : SurfaceHolder? = null
    var canvas : Canvas? = null

    var paint : Paint = Paint()

    //Adicionar a lista de primitivas quadrados e isso
    var circles = arrayListOf<Circle>()


    private fun init(context: Context?, width:Int, height: Int){


        surfaceHolder = holder



    }

    constructor(context: Context?, width:Int, height: Int) : super(context){
        init(context, width, height)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init(context,0,0)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        init(context,0,0)
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes){
        init(context,0,0)
    }


    override fun run() {
        while (playing){
            update()
            draw()
            control()
        }
    }

    private fun update(){

        //update
        for(s in circles){
            s.update()
        }

        //As vezes funciona outras vezes não por isso tentei criar um iterador, pq pensei que o erro que etsvaa a dar era por estar a remover
        //objetos enquanto está loop
        val iterator = circles.iterator()

        //Só entra aqui se tiver 2 circulos
        if (circles.count() >= 2)
        {
            //Percorre o mesmo array duas vezes
            for(s1 in iterator)
            {
                for (s2 in iterator)
                {
                    //Se interstarem
                    if (sqrt((s1.x - s2.x).pow(2) + (s1.y - s2.y).pow(2)) < (s1.raio + s2.raio))
                    {
                        //Verificar se é o mesmo circulo
                        if (circles.indexOf(s1).equals(circles.indexOf(s2)))
                        {
                            //Se for nao faz nada
                        }
                        //Se não for o mesmo circulo remove
                        else
                        {   circles.remove(s1)
                            circles.remove(s2)
                        }

                    }
                }

            }

        }



    }

    private fun draw(){
        surfaceHolder?.let {
            if (it.surface.isValid){
                canvas = surfaceHolder?.lockCanvas()

                //cor do canvas
                canvas?.drawColor(Color.BLACK)

                //cor dos objetos a desenhar
                paint.color = Color.WHITE

                //percorre o número de retangulos e desenha

                //Só desenha se tiver pelo menos um elemento na lista
                if (circles.count() >= 1)
                {
                    for(c in circles){
                        //centro, raio, e cor
                        canvas?.drawCircle(c.x, c.y, c.raio, paint)
                    }
                }



                surfaceHolder?.unlockCanvasAndPost(canvas)
            }
        }
    }

    private fun control(){
        Thread.sleep(17L)
    }

    fun pause(){
        playing = false
        gameThread?.join()
    }

    fun resume(){
        playing = true
        gameThread = Thread(this)
        gameThread!!.start()
    }

    //Ações a executar quando algum botão é pressionado
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action){


            //Se carregar no ecrã
            MotionEvent.ACTION_DOWN ->{

                //adicionar à lista e no ecrã onde a pessoa carregou

                //Tentei limitar que não se pudesse criar circulos dentro de circulos mas continuou a dar erro
                //if (circles.count() >= 1)
                //{
                //    for (c in circles)
                //    {
                //
                //
                //        //Se for um circulo
                //        if (event.x > c.x - c.raio && event.x < c.x + c.raio)
                //        {
                //            if (c.y - c.raio < event.y && c.y + c.raio > event.y)
                //            {
                //                //n faz nada
                //            }
                //            else
                //            {
                //                circles.add(Circle(width, height, event.x, event.y))
                //            }
                //        }
                //
                //
                //    }
                //}
                //else
                //{
                circles.add(Circle(width, height, event.x, event.y))
                //}




            }
        }
        return true
    }

}