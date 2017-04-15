/**
  * Created by Tom on 24/01/2017.
  */
import scala.io.StdIn
import scala.util.Random
object Hammurabi extends App {
  def printIntroductoryMessage(): Unit = {
    System.out.println("""Congratulations, you are the newest ruler of ancient Samaria, elected
                         |for a ten year term of office. Your duties are to dispense food, direct
                         |farming, and buy and sell land as needed to support your people. Watch
                         |out for rat infestations and the plague! Grain is the general currency,
                         |measured in bushels. The following will help you in your decisions:
                         |* Each person needs at least 20 bushels of grain per year to survive.
                         |* Each person can farm at most 10 acres of land.
                         |* It takes 2 bushels of grain to farm an acre of land.
                         |* The market price for land fluctuates yearly.
                         |Rule wisely and you will be showered with appreciation at the end of
                         |your term. Rule poorly and you will be kicked out of office!""")

    //var answer = scala.io.StdIn.readLine().toInt

  }

    var starved = 0            // how many people starved
    var immigrants = 5         // how many people came to the city
    var population = 100
    var harvest = 3000          // total bushels harvested
    var bushelsPerAcre = 3      // amount harvested for each acre planted
    var rats_ate = 200          // bushels destroyed by rats
    var bushelsInStorage = 2800
    var acresOwned = 1000
    var pricePerAcre = 19       // each acre costs this many bushels
    var plagueDeaths = 0
    for (i <- 1 to 10) {
      System.out.println(""" O great Hammurabi!
                           |        You are in year """+ i +""" of your ten year rule.
                                                              |      In the previous year """+starved+""" people starved to death.
                                                              |        In the previous year """+immigrants+""" people entered the kingdom.
                                                              |        The population is now """+population+""".
                                                              |      We harvested """+harvest+""" bushels at """+bushelsPerAcre+""" bushels per acre.
                                                              |      Rats destroyed """+rats_ate+""" bushels, leaving """+bushelsInStorage+""" bushels in storage.
                                                              |      The city owns """+acresOwned+""" acres of land.
                                                              |      Land is currently worth """+pricePerAcre+""" bushels per acre.
                                                              |      There were """+plagueDeaths+""" deaths from the plague.""")
      var x = askHowManyAcrestoPlant(acresOwned, bushelsInStorage)
      bushelsInStorage -= x
      acresOwned += x
      var y = askHowMuchGrainForPeople(bushelsInStorage, population)

      bushelsInStorage = bushelsInStorage - y
      starved = peopleFed(population, y)


      var t = askHowMuchLandToBuy(bushelsInStorage, pricePerAcre)
      bushelsInStorage -= t
      acresOwned += t
      var v = askHowMuchLandToSell(acresOwned, pricePerAcre)
      acresOwned -= v
      bushelsInStorage += v * pricePerAcre
      pricePerAcre = landPrice(acresOwned)
      plagueDeaths = plagueChange(population)
      immigrants = immigration(population, acresOwned, bushelsInStorage)
      population += immigrants
      population -= plagueDeaths
      harvest = harvest(acresOwned)
      rats_ate = rats(bushelsInStorage)
    }
  summary(starved, acresOwned)
    def summary(starved: Int, acres: Int): Unit = {
      var success = acres - starved
      if (success > 0) {
        "You did it!"
      } else {
        "Failed!"
      }
    }
    def landPrice(acre: Int) = {
      var cost = 17 + (scala.math.random * 6) * acre
      cost.toInt
    }
    def plagueChange(population: Int) = {
      var x = 100 * scala.math.random
      if (x > 15) {
        0
      } else {
        population / 2
      }
    }
    def peopleFed(population: Int, bushels: Int): Int = {
      var starved = bushels - population * 20
      if (starved < 1) {
        return 0
      }
      starved
    }
    def immigration(poulation: Int, acres: Int, bushels: Int) = {
      var immigrants = (20 * acres + bushels) / (100 * population) + 1
      immigrants
    }
    def harvest(acres: Int) = {
      var harvestTurnOut = Random.nextInt(8)+1 * acres
      println(acres)
      println(harvestTurnOut)
      harvestTurnOut.toInt
    }
    def rats(grain: Int): Int = {
      var consume = 0.0;
      var infestation = 2/5 * scala.math.random * 100
      if (infestation > 40) {
        return 0
      } else {
        consume = grain / ((scala.math.random * 2.0) + 1.0)
      }
      consume.toInt
    }
    def readInt(message: String): Int = {
      println(message)
      try {
        return StdIn.readLine().toInt
      } catch {
        case _ : Throwable =>
          println("Not an integeter.")
          return StdIn.readLine().toInt
      }
    }
    def askHowMuchLandToBuy(bushels: Int, price: Int) = {
      var acresToBuy = readInt("How many acres will you buy? ")
      while (acresToBuy < 0 || acresToBuy * price > bushels) {
        println("O Great Hammurabi, we have but " + bushels + " bushels of grain!")
        acresToBuy = readInt("How many acres will you buy? ")
      }
      acresToBuy
    }
    def askHowMuchLandToSell(acres: Int, price: Int) = {
      var acresToSell = readInt("How many acres will you sell?")
      while (acresToSell < 0) {
        println("O Great Hammurabi, we have but " + acres + " acres of land!")
      }
      acresToSell
    }
    def askHowMuchGrainForPeople(bushels: Int, people: Int) = {
      var bushelsforPeople = readInt("How much grain will you give to the people?")
      while (bushelsforPeople > bushels) {
        bushelsforPeople = readInt("O Great Hammurabi, we have but " + bushels + " bushels of grain!")
      }
      bushelsforPeople
    }
    def askHowManyAcrestoPlant(acres: Int, bushels: Int) = {
      //println("How many acres will you plant?")
      if (bushels < 1) {
        println("O Great Hammurabi, we have no more bushels to sew!")
        0
      }
      var acresToPlant = readInt("How many acres will you plant?")
      while (acresToPlant > acres) {
        println("O Great Hammurabit, we have but " + " acres of land!")
      }
      acresToPlant
    }
    printIntroductoryMessage()
    def hammurabi: Unit = {
      printIntroductoryMessage()
      var acresToBuy = askHowMuchLandToBuy(bushelsInStorage, pricePerAcre)
      acresOwned = acresOwned + acresToBuy
    }
    //Hammurabi.hammurabi

  Hammurabi.hammurabi
}
