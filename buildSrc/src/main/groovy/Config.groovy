class Config {
    static TRAINEES = [
            'aruga',
            'haneda',
            'hasegawa',
            'hayashi',
            'kamiya',
            'kato',
            'kito',
            'nakamura',
            'shiro',
            'sugimoto',
            'tsuguka'
    ]

    static JPL_EX = [
            1 : 1..16,
            2 : 1..18,
            3 : 1..12,
            4 : 1..6,
            5 : 1..2,
            6 : 1..5,
            7 : 1..3,
            8 : [],
            9 : 1..4,
            10: 1..5,
            11: 1..3,
            12: 1..2,
            13: 1..6,
            14: 1..10,
            15: [],
            16: (1..12) - [6, 7, 8, 10],
            17: 1..5,
            18: [],
            19: 1..2,
            20: 1..11,
            21: 1..7,
            22: 1..15,
            23: 1..3,
            24: 1..3,
            25: []
    ]

    static GUI_EX = [
            1: 1..4,
            2: 1..4
    ]

    static TOTAL
    static TOTAL_OF_JPL
    static TOTAL_OF_GUI
    static TOTAL_OF_INTERPRET

    static {
        TOTAL_OF_JPL = 0
        Config.JPL_EX.each { ch, exercises ->
            TOTAL_OF_JPL += exercises.size()
        }

        TOTAL_OF_GUI = 0
        Config.GUI_EX.each { ch, exercises ->
            TOTAL_OF_GUI += exercises.size()
        }

        TOTAL_OF_INTERPRET = 1

        TOTAL = TOTAL_OF_JPL + TOTAL_OF_GUI + TOTAL_OF_INTERPRET
    }
}
