package fr.splayce.REL

import org.specs2.mutable._

class RECstSpec extends Specification {

  import Symbols._
  import Implicits.RE2String
  import scala.collection.immutable.NumericRange
  import org.specs2.matcher.Matcher

  def matchChars(matches: Boolean, chars: Seq[Char]): Matcher[RECst] = 
    be_==(true) ^^ { (re: RE) => chars.foldLeft(true)(_ && _.toString.matches(re) == matches) }
  
  val matchAll = matchChars(true, _: Seq[Char])
  val matchNone = matchChars(false, _: Seq[Char])
  
  val a_z = 'a' to 'z'
  val A_Z = 'A' to 'Z'
  val `0_9` = '0' to '9'
  val à_ÿ = ('à' to 'ÿ').filter(_ != '÷')
  val À_Ý = ('À' to 'Ý').filter(_ != '×')

  "Epsilon" should {
    "linearize to an empty string" in {
      ε.toString must be empty
    }
    "not modify other constant regexes" in {
      (ε ~ α) === α
      (ε ~ Α) === Α
      (ε ~ λ) === λ
      (ε ~ Λ) === Λ
      (ε ~ δ) === δ
      (ε ~ Δ) === Δ
      (ε ~ σ) === σ
      (ε ~ Σ) === Σ
      (ε ~ μ) === μ
      (ε ~ Μ) === Μ
      (ε ~ ß) === ß
      (ε ~ Β) === Β
      (α ~ ε) === α
      (Α ~ ε) === Α
      (δ ~ ε) === δ
      (Δ ~ ε) === Δ
      (σ ~ ε) === σ
      (Σ ~ ε) === Σ
      (μ ~ ε) === μ
      (Μ ~ ε) === Μ
      (ß ~ ε) === ß
      (Β ~ ε) === Β
    }
  }

  "AlphaLower" should {
    "match a-z" in      { AlphaLower must matchAll (a_z) }
    "not match A-Z" in  { AlphaLower must matchNone (A_Z) }
    "not match 0-9" in  { AlphaLower must matchNone (`0_9`) }
    "not match à-ö" in  { AlphaLower must matchNone (à_ÿ) }
  }
  "AlphaUpper" should {
    "not match a-z" in  { AlphaUpper must matchNone (a_z) }
    "match A-Z" in      { AlphaUpper must matchAll (A_Z) }
    "not match 0-9" in  { AlphaUpper must matchNone (`0_9`) }
    "not match À-Ö" in  { AlphaUpper must matchNone (À_Ý) }
  }
  "Alpha" should {
    "match a-z" in      { Alpha must matchAll (a_z) }
    "match A-Z" in      { Alpha must matchAll (A_Z) }
    "not match 0-9" in  { Alpha must matchNone (`0_9`) }
    "not match à-ö" in  { Alpha must matchNone (`à_ÿ`) }
    "not match À-Ö" in  { Alpha must matchNone (À_Ý) }
  }
  "LetterLower" should {
    "match a-z" in      { LetterLower must matchAll (a_z) }
    "not match A-Z" in  { LetterLower must matchNone (A_Z) }
    "not match 0-9" in  { LetterLower must matchNone (`0_9`) }
    "match à-ö" in      { LetterLower must matchAll (à_ÿ) }
    "not match À-Ö" in  { LetterLower must matchNone (À_Ý) }
  }
  "LetterUpper" should {
    "not match a-z" in  { LetterUpper must matchNone (a_z) }
    "match A-Z" in      { LetterUpper must matchAll (A_Z) }
    "not match 0-9" in  { LetterUpper must matchNone (`0_9`) }
    "not match à-ö" in  { LetterUpper must matchNone (à_ÿ) }
    "match À-ß" in      { LetterUpper must matchAll (À_Ý) }
  }
  "Letter" should {
    "match a-z" in      { Letter must matchAll (a_z) }
    "match A-Z" in      { Letter must matchAll (A_Z) }
    "not match 0-9" in  { Letter must matchNone (`0_9`) }
    "match à-ö" in      { Letter must matchAll (`à_ÿ`) }
    "match À-Ö" in      { Letter must matchAll (À_Ý) }
  }
}