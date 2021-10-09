package com.example.grindspace

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mytimer: CountDownTimer? = null
    private var mybreak: CountDownTimer? = null
    private var bgBreak: CountDownTimer? = null
    var sec = 60
    var min = 0
    var btm = 0
    var btsec = 60
    var count = 0
    var isrunning = false
    var totaltime = 0L
    var totaltimeb = 0L
    var case = false
    var smin = 0
    var sbtm = 0
    var progdiv = 10
    var prog =600


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timer_sec_progress.max = 600

        spinnerwt()


    }


    private fun timealgo() {
        b1.setOnClickListener {
            if (!isrunning) {
                startBttn()
            } else {
                resetTimer()
            }
        }
        b2.setOnClickListener {
            if (!isrunning) {
                resetTimer()
            } else {
                stopBttn()
            }
        }
    }


    private fun spinnerwt() {
        val adapter = ArrayAdapter.createFromResource(this, R.array.work_time, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp1.adapter = adapter
        sp1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val wt = parent!!.getItemAtPosition(position)
                if (position != 0) {
                    if (position == 1) {
                        smin = 44
                        totaltime = 2700000
                    } else {
                        if (position == 2) {
                            smin = 34
                            totaltime = 2100000
                        } else {
                            smin = 24
                            totaltime = 1500000
                        }
                    }

                    spinnerbt()
                } else
                    onNothingSelected(parent)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                tv2.text = "00:00"
            }

        }
    }

    private fun spinnerbt() {
        val adapter = ArrayAdapter.createFromResource(this, R.array.break_time, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp2.adapter = adapter
        sp2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val bts = parent!!.getItemAtPosition(position)
                if (position != 0) {
                    if (position == 1) {
                        sbtm = 4
                        totaltimeb = 300000
                        case = true
                    } else {
                        if (position == 2) {
                            sbtm = 9
                            totaltimeb = 600000
                            case = true
                        } else {
                            if (smin != 24 && position == 3) {
                                sbtm = 15
                                totaltimeb = 900000
                                case = true
                            } else {
                                if (smin == 24 && position == 3) {
                                    case = false
                                    onNothingSelected(parent)
                                }
                            }

                        }
                    }
                    if (case == true) {
                        timealgo()
                    }

                } else {
                    onNothingSelected(parent)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                tv2.text = "00:00"
            }

        }

    }


    private fun startBttn() {
        isrunning = true
        min = smin

        mytimer = object : CountDownTimer(totaltime, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {


                sec--
                if (sec >= 10) {
                    tv2.text = "$min:$sec"
                } else {
                    tv2.text = "$min:0$sec"
                }
                if (min > 0 && sec == 0) {
                    min--

                    sec = 60
                    prog = 600
                }else{
                    prog -= progdiv
                }
                updateProg()

            }

            override fun onFinish() {
                count++
                isrunning = false
                tv2.text = "$btm:$btsec"

                sec = 60
                if (count <= 2) {
                    breakTime()
                } else {
                    tv2.text = "GREAT JOB"
                    count = 0
                }

                prog = 600
                updateProg()
            }
        }
        (mytimer as CountDownTimer).start()
    }

    private fun breakTime() {
        btm = sbtm

        isrunning = true

        tv2.text = "$btm:$btsec"
        mybreak = object : CountDownTimer(totaltimeb, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                btsec--
                if (btsec >= 10) {
                    tv2.text = "$btm:$btsec"
                } else {
                    tv2.text = "$btm:0$btsec"
                }
                if (btm > 0 && btsec == 0) {
                    btm--

                    btsec = 60
                    prog = 600
                }else{
                    prog -= progdiv
                }
                updateProg()
            }

            override fun onFinish() {
                min = smin
                sec = 60
                tv2.text = "$min:$sec"
                prog = 600
                updateProg()
                startBttn()



            }
        }
        (mybreak as CountDownTimer).start()
    }

    private fun stopBttn() {
        isrunning = false
        mytimer!!.cancel()
        prog = 600
        updateProg()
    }

    private fun resetTimer() {
        if (!isrunning) {
            sec = 60
            min = smin
            tv2.text = "$min:59"
        } else (
                stopBttn()
                )
    }

    private fun updateProg(){

        timer_sec_progress.progress = prog
    }


}