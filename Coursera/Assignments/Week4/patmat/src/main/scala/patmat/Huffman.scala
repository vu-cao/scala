package patmat

import cafesat.api.Formulas.True
import common._

/**
 * Assignment 4: Huffman coding
 *
 */
object Huffman {

  /**
   * A huffman code is represented by a binary tree.
   *
   * Every `Leaf` node of the tree represents one character of the alphabet that the tree can encode.
   * The weight of a `Leaf` is the frequency of appearance of the character.
   *
   * The branches of the huffman tree, the `Fork` nodes, represent a set containing all the characters
   * present in the leaves below it. The weight of a `Fork` node is the sum of the weights of these
   * leaves.
   */
    abstract class CodeTree
  case class Fork(left: CodeTree, right: CodeTree, chars: List[Char], weight: Int) extends CodeTree
  case class Leaf(char: Char, weight: Int) extends CodeTree
  

  // Part 1: Basics
    def weight(tree: CodeTree): Int = {
      tree match {
        case Leaf(_, w) => w
        case Fork(_, _, _, w) => w
      }
    }
  
    def chars(tree: CodeTree): List[Char] = {
      tree match {
        case Leaf(c, _) => c :: Nil
        case Fork(_, _, c, _) => c
      }
    }
  
  def makeCodeTree(left: CodeTree, right: CodeTree) =
    Fork(left, right, chars(left) ::: chars(right), weight(left) + weight(right))



  // Part 2: Generating Huffman trees

  /**
   * In this assignment, we are working with lists of characters. This function allows
   * you to easily create a character list from a given string.
   */
  def string2Chars(str: String): List[Char] = str.toList

  /**
   * This function mber of
   * times it occurs. For example, the invocationcomputes for each unique character in the list `chars` the nu
   *
   *   times(List('a', 'b', 'a'))
   *
   * should return the following (the order of the resulting list is not important):
   *
   *   List(('a', 2), ('b', 1))
   *
   * The type `List[(Char, Int)]` denotes a list of pairs, where each pair consists of a
   * character and an integer. Pairs can be constructed easily using parentheses:
   *
   *   val pair: (Char, Int) = ('c', 1)
   *
   * In order to access the two elements of a pair, you can use the accessors `_1` and `_2`:
   *
   *   val theChar = pair._1
   *   val theInt  = pair._2
   *
   * Another way to deconstruct a pair is using pattern matching:
   *
   *   pair match {
   *     case (theChar, theInt) =>
   *       println("character is: "+ theChar)
   *       println("integer is  : "+ theInt)
   *   }
   */
    def times(chars: List[Char]): List[(Char, Int)] = {
      def insert(c: Char, result: List[(Char, Int)]): List[(Char, Int)] = {
        result match {
          case Nil => List((c, 1))
          case head::tail => {
            if (head._1 == c) (head._1, head._2 + 1)::tail
            else head::insert(c, tail)
          }
        }
      }
      def execute(chars: List[Char], result: List[(Char, Int)]): List[(Char, Int)] = {
        chars match {
          case Nil => result
          case head::tail => {
            val r = insert(head, result)
            execute(tail, r)
          }
        }
      }
      execute(chars, List())
    }
  
  /**
   * Returns a list of `Leaf` nodes for a given frequency table `freqs`.
   *
   * The returned list should be ordered by ascending weights (i.e. the
   * head of the list should have the smallest weight), where the weight
   * of a leaf is the frequency of the character.
   */
    def makeOrderedLeafList(freqs: List[(Char, Int)]): List[Leaf] = {
      def insert(pair: (Char, Int), result: List[Leaf]): List[Leaf] = result match {
        case Nil => Leaf(pair._1, pair._2)::Nil
        case head::tail =>
          if (pair._2 <= head.weight) Leaf(pair._1, pair._2)::result
          else head::insert(pair, tail)
      }

      def execute(input: List[(Char, Int)], result: List[Leaf]): List[Leaf] = input match {
        case Nil => result
        case head::tail => {
          val r = insert(head, result)
          execute(tail, r)
        }
      }

      execute(freqs,Nil)
  }
  
  /**
   * Checks whether the list `trees` contains only one single code tree.
   */
    def singleton(trees: List[CodeTree]): Boolean = trees.size == 1


  
  /**
   * The parameter `trees` of this function is a list of code trees ordered
   * by ascending weights.
   *
   * This function takes the first two elements of the list `trees` and combines
   * them into a single `Fork` node. This node is then added back into the
   * remaining elements of `trees` at a position such that the ordering by weights
   * is preserved.
   *
   * If `trees` is a list of less than two elements, that list should be returned
   * unchanged.
   */
    def combine(trees: List[CodeTree]): List[CodeTree] = {
      def execute(node1: CodeTree, node2: CodeTree): Fork = {
        var chars2: List[Char] = Nil
        var weight2: Int = 0
        node2 match {
          case Leaf(c, w) => {
            chars2 = c::chars2
            weight2 = w
          }
          case Fork(_, _, chars, weight) => {
            chars2 = chars
            weight2 = weight
          }
        }

        var weight1: Int = 0
        node1 match {
          case Leaf(c, w) => {
            chars2 = c::chars2
            weight1 = w
          }
          case Fork(_, _, chars, weight) => {
            def merge2Lists(chars1: List[Char], chars2: List[Char]): List[Char] = {
              chars1 match {
                case Nil => chars2
                case head::tail => merge2Lists(tail, head::chars2)
              }
            }
            chars2 = merge2Lists(chars, chars2)
            weight1 = weight
          }
        }

        Fork(node1, node2, chars2, weight1 + weight2)
      }

        trees match {
          case head1::head2::tail => {
            val newNode = execute(head1, head2)
            newNode::tail
          }
          case _ => trees
        }
      }
  
  /**
   * This function will be called in the following way:
   *
   *   until(singleton, combine)(trees)
   *
   * where `trees` is of type `List[CodeTree]`, `singleton` and `combine` refer to
   * the two functions defined above.
   *
   * In such an invocation, `until` should call the two functions until the list of
   * code trees contains only one single tree, and then return that singleton list.
   *
   * Hint: before writing the implementation,
   *  - start by defining the parameter types such that the above example invocation
   *    is valid. The parameter types of `until` should match the argument types of
   *    the example invocation. Also define the return type of the `until` function.
   *  - try to find sensible parameter names for `xxx`, `yyy` and `zzz`.
   */
    def until(isSingleTree: List[CodeTree] => Boolean, combineCodeTrees: List[CodeTree] => List[CodeTree])(trees: List[CodeTree]): CodeTree = {
      if (isSingleTree(trees)) trees.head
      else {
        val newCodeTrees: List[CodeTree] = combineCodeTrees(trees)
        until(isSingleTree, combineCodeTrees)(newCodeTrees)
      }
  }
  
  /**
   * This function creates a code tree which is optimal to encode the text `chars`.
   *
   * The parameter `chars` is an arbitrary text. This function extracts the character
   * frequencies from that text and creates a code tree based on them.
   */
    def createCodeTree(chars: List[Char]): CodeTree = {
      val leavesList = makeOrderedLeafList(times(chars))
      until(singleton, combine)(leavesList)
  }
  

  // Part 3: Decoding

  type Bit = Int

  /**
   * This function decodes the bit sequence `bits` using the code tree `tree` and returns
   * the resulting list of characters.
   */
    def decode(tree: CodeTree, bits: List[Bit]): List[Char] = {
      def execute(newTree: CodeTree, bits: List[Bit], result: List[Char]): List[Char] = {
        def append(c: Char, list: List[Char]): List[Char] = {
          list match {
            case Nil => List(c)
            case head::tail => head::append(c, tail)
          }
        }

        newTree match {
          case Leaf(c, _) => {
            val newResult = append(c, result)
            execute(tree, bits, newResult)
          }
          case Fork(node1, node2, _, _) =>
            bits match {
              case Nil => result
              case head::tail =>
                if (head == 0) execute(node1, tail, result)
                else execute(node2, tail, result)
            }
        }
      }
      execute(tree, bits, Nil)
    }
  
  /**
   * A Huffman coding tree for the French language.
   * Generated from the data given at
   *   http://fr.wikipedia.org/wiki/Fr%C3%A9quence_d%27apparition_des_lettres_en_fran%C3%A7ais
   */
  val frenchCode: CodeTree = Fork(Fork(Fork(Leaf('s',121895),Fork(Leaf('d',56269),Fork(Fork(Fork(Leaf('x',5928),Leaf('j',8351),List('x','j'),14279),Leaf('f',16351),List('x','j','f'),30630),Fork(Fork(Fork(Fork(Leaf('z',2093),Fork(Leaf('k',745),Leaf('w',1747),List('k','w'),2492),List('z','k','w'),4585),Leaf('y',4725),List('z','k','w','y'),9310),Leaf('h',11298),List('z','k','w','y','h'),20608),Leaf('q',20889),List('z','k','w','y','h','q'),41497),List('x','j','f','z','k','w','y','h','q'),72127),List('d','x','j','f','z','k','w','y','h','q'),128396),List('s','d','x','j','f','z','k','w','y','h','q'),250291),Fork(Fork(Leaf('o',82762),Leaf('l',83668),List('o','l'),166430),Fork(Fork(Leaf('m',45521),Leaf('p',46335),List('m','p'),91856),Leaf('u',96785),List('m','p','u'),188641),List('o','l','m','p','u'),355071),List('s','d','x','j','f','z','k','w','y','h','q','o','l','m','p','u'),605362),Fork(Fork(Fork(Leaf('r',100500),Fork(Leaf('c',50003),Fork(Leaf('v',24975),Fork(Leaf('g',13288),Leaf('b',13822),List('g','b'),27110),List('v','g','b'),52085),List('c','v','g','b'),102088),List('r','c','v','g','b'),202588),Fork(Leaf('n',108812),Leaf('t',111103),List('n','t'),219915),List('r','c','v','g','b','n','t'),422503),Fork(Leaf('e',225947),Fork(Leaf('i',115465),Leaf('a',117110),List('i','a'),232575),List('e','i','a'),458522),List('r','c','v','g','b','n','t','e','i','a'),881025),List('s','d','x','j','f','z','k','w','y','h','q','o','l','m','p','u','r','c','v','g','b','n','t','e','i','a'),1486387)

  /**
   * What does the secret message say? Can you decode it?
   * For the decoding use the 'frenchCode' Huffman tree defined above.
   */
  val secret: List[Bit] = List(0,0,1,1,1,0,1,0,1,1,1,0,0,1,1,0,1,0,0,1,1,0,1,0,1,1,0,0,1,1,1,1,1,0,1,0,1,1,0,0,0,0,1,0,1,1,1,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,1)

  /**
   * Write a function that returns the decoded secret
   */
    def decodedSecret: List[Char] = {
      decode(frenchCode, secret)
    }
  

  // Part 4a: Encoding using Huffman tree

  /**
   * This function encodes `text` using the code tree `tree`
   * into a sequence of bits.
   */
    def encode(tree: CodeTree)(text: List[Char]): List[Bit] = {
      def choosePath(node: CodeTree, char:Char): Boolean = {
        node match {
          case Leaf(c, _) =>
            if (c == char) true
            else false
          case Fork(_, _, chars, _) =>
            if (chars.contains(char)) true
            else false
        }
      }
      def append(bit: Bit, list: List[Bit]): List[Bit] = {
        list match {
          case Nil => bit::Nil
          case head::tail => head::append(bit, tail)
        }
      }
      def encodeAChar(tree: CodeTree)(char: Char, result: List[Bit]): List[Bit] = {
        tree match {
          case Leaf(c, _) =>
            if (c == char) result
            else throw new IllegalArgumentException
          case Fork(node1, node2, _, _) => {
            if (choosePath(node1, char)) {
              val newResult = append(0, result)
              encodeAChar(node1)(char, newResult)
            }
            else
              if (choosePath(node2, char)) {
                val newResult = append(1, result)
                encodeAChar(node2)(char, newResult)
              }
              else throw new IllegalArgumentException
          }
        }
      }
      def execute(tree: CodeTree)(text:List[Char], result: List[Bit]): List[Bit] = {
        text match {
          case Nil => result
          case head::tail => {
            val newResult = encodeAChar(tree)(head, result)
            execute(tree)(tail, newResult)
          }
        }
      }
      execute(tree)(text, Nil)
    }
  
  // Part 4b: Encoding using code table

  type CodeTable = List[(Char, List[Bit])]

  /**
   * This function returns the bit sequence that represents the character `char` in
   * the code table `table`.
   */
    def codeBits(table: CodeTable)(char: Char): List[Bit] = {
      table match {
        case Nil => throw new IllegalArgumentException
        case head::tail =>
          if (head._1 == char) head._2
          else codeBits(tail)(char)
      }
    }
  
  /**
   * Given a code tree, create a code table which contains, for every character in the
   * code tree, the sequence of bits representing that character.
   *
   * Hint: think of a recursive solution: every sub-tree of the code tree `tree` is itself
   * a valid code tree that can be represented as a code table. Using the code tables of the
   * sub-trees, think of how to build the code table for the entire tree.
   */
    def convert(tree: CodeTree): CodeTable = {
      def append(bit: Bit, list: List[Bit]): List[Bit] = {
        list match {
          case Nil => bit::Nil
          case head::tail => head::append(bit, tail)
        }
      }
      def execute(tree: CodeTree, list: List[Bit], result: CodeTable): CodeTable = {
        tree match {
          case Leaf(c, _) => (c, list)::result
          case Fork(node1, node2, _, _) => {
            val newList0 = append(0, list)
            execute(node1, newList0, result)
            val newList1 = append(1, list)
            execute(node2, newList1, result)
          }
        }
      }
      execute(tree, Nil, Nil)
    }
  
  /**
   * This function takes two code tables and merges them into one. Depending on how you
   * use it in the `convert` method above, this merge method might also do some transformations
   * on the two parameter code tables.
   */
    def mergeCodeTables(a: CodeTable, b: CodeTable): CodeTable = {
      a match {
        case Nil => b
        case head::tail => head::mergeCodeTables(tail, b)
      }
    }

  /**
   * This function encodes `text` according to the code tree `tree`.
   *
   * To speed up the encoding process, it first converts the code tree to a code table
   * and then uses it to perform the actual encoding.
   */
    def quickEncode(tree: CodeTree)(text: List[Char]): List[Bit] = {
      val codeTable = convert(tree)
      def append(l: List[Bit], list: List[Bit]): List[Bit] = {
        list match {
          case Nil => l
          case head::tail => head::append(l, tail)
        }
      }
      def findChar(codeTable: CodeTable)(char: Char): List[Bit] = {
        codeTable match {
          case Nil => throw new Error("Empty Code Table")
          case head::tail =>
            if (head._1 == char) head._2
            else findChar(tail)(char)
        }
      }
      def execute(codeTable: CodeTable)(text: List[Char], result: List[Bit]): List[Bit] = {
        text match {
          case Nil => Nil
          case head::tail => {
            val newResult = append(findChar(codeTable)(head), result)
            execute(codeTable)(tail, newResult)
          }
        }
      }
    }
  }
