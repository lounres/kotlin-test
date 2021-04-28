package math.varia.naturalDeduction

sealed class LogicalExpression {
    data class Propositional(val name: String)
    data class Conjugation(val first: LogicalExpression, val second: LogicalExpression)
    data class Disjunction(val first: LogicalExpression, val second: LogicalExpression)
    data class Implication(val first: LogicalExpression, val second: LogicalExpression)
    data class Negation(val expr: LogicalExpression)
}