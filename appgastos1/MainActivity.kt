package com.luish.appgastos1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class TransactionType(val title: String) {
    object IngresoFijo : TransactionType("Ingreso Fijo")
    object IngresoVariable : TransactionType("Ingreso Variable")
    object GastoFijo : TransactionType("Gasto Fijo")
    object GastoVariable : TransactionType("Gasto Variable")
    object Ahorro : TransactionType("Ahorro")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aquí se inicia la navegación
            val navController = rememberNavController()
            SetupNavGraph(navController = navController)
        }
    }
}

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // Pantalla de Inicio de Sesión
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("menu")
                }
            )
        }

        // Pantalla del Menú
        composable("menu") {
            MenuScreen(
                onNavigateToIngresoFijo = { navController.navigate("ingreso_fijo") },
                onNavigateToIngresosVar = { navController.navigate("ingresos_var") },
                onNavigateToGastosFijos = { navController.navigate("gastos_fijos") },
                onNavigateToGastosVar = { navController.navigate("gastos_var") },
                onNavigateToAhorros = { navController.navigate("ahorros") },
                onNavigateToIngresoScreen = { navController.navigate("ingreso_screen") }
            )
        }

        // Pantalla de Ingreso Fijo
        composable("ingreso_fijo") {
            IngresoFijoScreen(
                title = "Ingresos Fijos",
                queryDate = "Octubre 2024",
                transactions = listOf(
                    Transaction("Sueldo", "01-10-2024", 1500f),
                    Transaction("Alquiler", "05-10-2024", 700f),
                    Transaction("Negocio", "10-10-2024", 300f),
                    Transaction("Inversión", "15-10-2024", 500f)
                )
            )
        }

        // Pantalla de Ingresos Variables
        composable("ingresos_var") {
            IngresosVarScreen(
                title = "Ingresos Variables",
                queryDate = "Octubre 2024",
                transactions = listOf(
                    Transaction(description = "Comisiones", date = "02/10/2024", amount = 250f),
                    Transaction(description = "Donación", date = "05/10/2024", amount = 100f),
                    Transaction(description = "Regalo Cumple", date = "10/10/2024", amount = 300f),
                    Transaction(description = "Remesa", date = "20/10/2024", amount = 500f)
                )
            )
        }

        // Pantalla de Gastos Fijos
        composable("gastos_fijos") {
            GastosFijosScreen(
                title = "Gastos Fijos",
                queryDate = "Octubre 2024",
                transactions = listOf(
                    Transaction(description = "Telefono", date = "02/10/2024", amount = 150f),
                    Transaction(description = "Despensa", date = "05/10/2024", amount = 750f),
                    Transaction(description = "Luz", date = "10/10/2024", amount = 250f),
                    Transaction(description = "Agua", date = "20/10/2024", amount = 100f)
                )
            )
        }

        // Pantalla de Gastos Variables
        composable("gastos_var") {
            GastosVarScreen(
                title = "Gastos Variables",
                queryDate = "Octubre 2024",
                transactions = listOf(
                    Transaction(description = "Macdonald", date = "02/10/2024", amount = 50f),
                    Transaction(description = "Regalo Wicho", date = "05/10/2024", amount = 100f),
                    Transaction(description = "Desayuno", date = "10/10/2024", amount = 30f),
                    Transaction(description = "Pastel Oficina", date = "20/10/2024", amount = 70f)
                )
            )
        }

        // Pantalla de Ahorros
        composable("ahorros") {
            AhorrosScreen(
                title = "Ahorros",
                initialMonth = "", // Valor inicial vacío
                initialYear = "",  // Valor inicial vacío
                queryDate = "",
                transactions = listOf(
                    Transaction("Depósito sueldo", "10-01-2024", 1000f),
                    Transaction("Intereses", "15-01-2024", 50f),
                    Transaction("Depósito extra", "20-01-2024", 500f)
                )
            )
        }

        // Pantalla de Ingreso
        composable("ingreso_screen") {
            IngresoScreen(

            )
        }
    }
}

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    val LightGray = Color(0xFFF5F5F5) // Fondo claro
    val SoftWhite = Color(0xFFFAFAFA) // Alternativa de fondo claro
    val powderblue = Color(0xFFAECB9D)
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    // Usamos Surface para definir el fondo
    Surface(
        modifier = Modifier.fillMaxSize(), // Ocupa toda la pantalla
        color = powderblue // Aquí se define el color del fondo
    ) {
        // Contenido de la pantalla de inicio de sesión
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center, // Centra el contenido verticalmente
            horizontalAlignment = Alignment.CenterHorizontally // Centra horizontalmente
        ) {
            // Logo de foto de perfil
            Image(
                painter = painterResource(id = R.drawable.fotoperfil), // Reemplaza con tu imagen
                contentDescription = "Logo",
                modifier = Modifier
                    .height(150.dp) // Ajusta el tamaño del logo
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Iniciar Sesión",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                colors = TextFieldDefaults.colors(SoftWhite),
                onValueChange = { username = it },
                label = { Text("Usuario") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                colors = TextFieldDefaults.colors(SoftWhite),
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation() // Oculta el texto
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (username == "admin" && password == "admin") {
                        Log.d("LoginScreen", "Login exitoso")
                        onLoginSuccess() // Navegará al menú cuando el login sea exitoso
                    } else {
                        Log.d("LoginScreen", "Error en el login")

                        showError = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E3A8A),
                    contentColor = Color.White
                )
            ) {
                Text("Iniciar Sesión")
            }

            if (showError) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Usuario o contraseña incorrecta.",
                    color = Color.Red
                )
            }
        }
    }
}

@Composable
fun MenuScreen(
    onNavigateToIngresoFijo: () -> Unit,
    onNavigateToIngresosVar: () -> Unit,
    onNavigateToGastosFijos: () -> Unit,
    onNavigateToGastosVar: () -> Unit,
    onNavigateToAhorros: () -> Unit,
    onNavigateToIngresoScreen: () -> Unit
) {
    val scrollState = rememberScrollState()

    // Personalización de colores y tamaños
    val profilePictureSize = 0.20f   // Tamaño de la imagen de perfil (20% del ancho de la pantalla)
    val menuPadding = 20.dp          // Sangría de los menús desplegables

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFAECB9D)) // Fondo de la pantalla
            .padding(16.dp)
            .verticalScroll(scrollState),  // Para poder hacer scroll
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen de perfil redonda
        ProfileImage(profilePictureSize)

        Spacer(modifier = Modifier.height(35.dp))

        // Nombre de usuario
        Text(
            text = "Nombre de Usuario",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Divider antes de Ingresos
        Divider(color = Color.Gray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(10.dp))

        // Tarjeta para ingresos
        MenuCard(
            title = "Ingresos",
            items = listOf("Ingresos Fijos", "Ingresos Variables"),
            menuPadding = menuPadding,
            onClickItem = { item ->
                when (item) {
                    "Ingresos Fijos" -> onNavigateToIngresoFijo()
                    "Ingresos Variables" -> onNavigateToIngresosVar()
                }
            }
        )

        Divider(color = Color.Gray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(10.dp))

        // Tarjeta para gastos
        MenuCard(
            title = "Gastos",
            items = listOf("Gastos Fijos", "Gastos Variables"),
            menuPadding = menuPadding,
            onClickItem = { item ->
                when (item) {
                    "Gastos Fijos" -> onNavigateToGastosFijos()
                    "Gastos Variables" -> onNavigateToGastosVar()
                }
            }
        )

        Divider(color = Color.Gray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(10.dp))

        // Tarjeta para ahorros
        MenuCard(
            title = "Ahorros",
            items = listOf("Ver/Editar", "Eliminar"),
            menuPadding = menuPadding,
            onClickItem = {
                onNavigateToAhorros()
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Divider antes de las demás opciones
        Divider(color = Color.Gray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(10.dp))

        // Otras tarjetas aún no modificadas
        MenuOptionCard(title = "Presupuesto", onClick = { /* Navegar a la pantalla de Presupuesto */ }, menuPadding = menuPadding)
        Divider(color = Color.Gray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(10.dp))

        MenuOptionCard(title = "Cotizador", onClick = { /* Navegar a la pantalla de Cotizador */ }, menuPadding = menuPadding)
        Divider(color = Color.Gray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(10.dp))

        MenuOptionCard(
            title = "Ingresar Transacción",
            menuPadding = menuPadding,
            onClick = onNavigateToIngresoScreen
        )

        Divider(color = Color.Gray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(10.dp))

        MenuOptionCard(title = "Configuración", onClick = { /* Navegar a la pantalla de Configuración */ }, menuPadding = menuPadding)
    }
}

// Composable para la imagen de perfil redonda
@Composable
fun ProfileImage(sizePercentage: Float) {
    val imageModifier = Modifier
        .size((sizePercentage * 650).dp) // Tamaño proporcional al largo de la pantalla
        .aspectRatio(1f)
        .clip(CircleShape)
        .padding(8.dp)

    Image(
        painter = painterResource(id = R.drawable.fotoperfil),  // Asegúrate de que el nombre y extensión sean correctos
        contentDescription = "Imagen de perfil",
        modifier = imageModifier,
        contentScale = ContentScale.Crop
    )
}

// Modificación del composable MenuCard para aceptar el onClickItem
@Composable
fun MenuCard(title: String, items: List<String>, menuPadding: Dp, onClickItem: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(menuPadding),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE6E6FA) // Color de fondo de la tarjeta
        )
    ) {
        DropdownMenu(
            title = title,
            items = items,
            menuPadding = menuPadding,
            onClickItem = onClickItem)

    }
}

// Composable para las opciones del menú en tarjetas individuales
@Composable
fun MenuOptionCard(title: String, onClick: () -> Unit, menuPadding: Dp) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(menuPadding)
            .clickable { onClick() },  // Permite hacer clic en toda la tarjeta
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE6E6FA) // Color de fondo de la tarjeta
        )
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)  // Añade espacio alrededor del texto
        )
    }
}


// Modificación de DropdownMenu para manejar clicks
@Composable
fun DropdownMenu(title: String, items: List<String>, menuPadding: Dp, onClickItem: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = menuPadding)
    ) {
        Text(
            text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(vertical = 8.dp)
        )

        if (expanded) {
            items.forEach { item ->
                Text(
                    text = "- $item",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .clickable { onClickItem(item) }
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}

// Implementaciones de otras pantallas
@Composable
fun IngresoFijoScreen(
    title: String,
    initialMonth: String = "",  // Valor inicial vacío
    initialYear: String = "",   // Valor inicial vacío
    queryDate: String,
    transactions: List<Transaction>
) {
    val scrollState = rememberScrollState()

    // Calcular el total de ingresos
    val totalIngresos = transactions.sumOf { it.amount.toDouble() }

    // Estado para el mes y año seleccionados
    var selectedMonth by remember { mutableStateOf(initialMonth) }
    var selectedYear by remember { mutableStateOf(initialYear) }

    val months = listOf("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")
    val years = (2024..2034).toList().map { it.toString() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFAECB9D)) // Fondo
            .padding(16.dp)
    ) {
        // Logo y título
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.fotoperfil), // Imagen de perfil
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )

            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Filtros para Mes y Año
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 110.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Filtro de Mes
                Box(modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)) {
                    DropdownSelector(
                        label = "Mes de consulta",
                        options = months,
                        selectedOption = selectedMonth,
                        onOptionSelected = { selectedMonth = it }  // Actualiza el mes seleccionado
                    )
                }

                // Filtro de Año
                Box(modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)) {
                    DropdownSelector(
                        label = "Año de consulta",
                        options = years,
                        selectedOption = selectedYear,
                        onOptionSelected = { selectedYear = it }  // Actualiza el año seleccionado
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Encabezados de la tabla
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = "No.", fontSize = 13.sp, modifier = Modifier.weight(0.8f))
                Text(text = "Descripción", fontSize = 13.sp, modifier = Modifier.weight(2.5f))
                Text(text = "Fecha", fontSize = 13.sp, modifier = Modifier.weight(2f))
                Text(text = "Monto", fontSize = 13.sp, modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Transacciones
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            transactions.forEachIndexed { index, transaction ->
                TransactionCard(
                    number = index + 1,
                    transaction = transaction
                )
            }

            // Ingreso total al final
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Ingreso total: $totalIngresos",
                    fontSize = 15.sp,
                    modifier = Modifier.padding(10.dp),
                    color = Color.Black
                )
            }
        }

        // Botón de editar
        FloatingActionButton(
            onClick= { /* Acción de editar */ },
            containerColor = Color(0xFF1E3A8A),
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.End)
        ) {
            Text(text = "Editar", color = Color.White)
        }
    }
}

@Composable
fun TransactionCard(
    number: Int,
    transaction: Transaction
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = number.toString(), fontSize = 13.sp, modifier = Modifier.weight(0.8f))
            Text(text = transaction.description, fontSize = 13.sp, modifier = Modifier.weight(2.5f))
            Text(text = transaction.date, fontSize = 13.sp, modifier = Modifier.weight(2f))
            Text(text = "${transaction.amount}", fontSize = 13.sp, modifier = Modifier.weight(1f))
        }
    }
}
@Composable
fun DropdownSelector(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    // Si el selectedOption está vacío, mostramos "Seleccionar Mes" o "Seleccionar Año"
    val displayText = if (selectedOption.isEmpty()) "Seleccionar $label" else selectedOption

    Box(modifier = Modifier.fillMaxWidth(0.9f)) {
        Column {
            Text(text = label, fontSize = 12.sp, modifier = Modifier.padding(8.dp))

            // Caja de selección desplegable
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .clickable { expanded = true } // Al hacer clic, se despliega el menú
            ) {
                Text(
                    text = displayText, // Mostrar el texto por defecto o la opción seleccionada
                    modifier = Modifier.padding(8.dp),
                    color = if (selectedOption.isEmpty()) Color.Gray else Color.Black // Mostrar en gris si no hay selección
                )
            }

            // Menú desplegable
            androidx.compose.material3.DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.heightIn(max = 300.dp)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        },
                        text = { Text(option) }
                    )
                }
            }
        }
    }
}

data class Transaction(
    val description: String,
    val date: String,
    val amount: Float
)

@Composable
fun IngresosVarScreen(
    title: String,
    initialMonth: String = "",  // Valor inicial vacío
    initialYear: String = "",   // Valor inicial vacío
    queryDate: String,
    transactions: List<Transaction>
) {
    val scrollState = rememberScrollState()

    // Calcular el total de ingresos fuera del bucle
    val totalIngresos = transactions.sumOf { it.amount.toDouble() }

    // Estado para el mes y año seleccionados
    var selectedMonth by remember { mutableStateOf(initialMonth) }
    var selectedYear by remember { mutableStateOf(initialYear) }

    val months = listOf("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")
    val years = (2024..2034).toList().map { it.toString() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFAECB9D)) // Fondo
            .padding(16.dp)
    ) {
        // Logo y título
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.fotoperfil), // Imagen "fotoperfil.jpg"
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )

            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Filtros para Mes y Año
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 110.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Filtro de Mes
                Box(modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)) {
                    DropdownSelector(
                        label = "Mes de consulta",
                        options = months,
                        selectedOption = selectedMonth,
                        onOptionSelected = { selectedMonth = it }  // Actualiza el mes seleccionado
                    )
                }

                // Filtro de Año
                Box(modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)) {
                    DropdownSelector(
                        label = "Año de consulta",
                        options = years,
                        selectedOption = selectedYear,
                        onOptionSelected = { selectedYear = it }  // Actualiza el año seleccionado
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Encabezados de la tabla (centrados)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = "No.", fontSize = 13.sp, modifier = Modifier.weight(0.8f))
                Text(text = "Descripción", fontSize = 13.sp, modifier = Modifier.weight(2.5f))
                Text(text = "Fecha", fontSize = 13.sp, modifier = Modifier.weight(2f))
                Text(text = "Monto", fontSize = 13.sp, modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Transacciones en tarjetas con scroll
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            transactions.forEachIndexed { index, transaction ->
                TransactionCard(
                    number = index + 1,
                    transaction = transaction
                )
            }

            // Ingreso total al final
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Ingreso total: $totalIngresos",
                    fontSize = 15.sp,
                    modifier = Modifier.padding(10.dp),
                    color = Color.Black
                )
            }
        }

        // Botón de editar justo después de las transacciones
        FloatingActionButton(
            onClick= { /* Acción de editar */ },
            containerColor = Color(0xFF1E3A8A),
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.End)
        ) {
            Text(text = "Editar", color = Color.White)
        }
    }
}

@Composable
fun GastosFijosScreen(
    title: String,
    initialMonth: String = "",  // Valor inicial vacío
    initialYear: String = "",   // Valor inicial vacío
    queryDate: String,
    transactions: List<Transaction>
) {
    val scrollState = rememberScrollState()
    // Calcular el total de ingresos fuera del bucle
    var totalgastos = transactions.sumOf { it.amount.toDouble() }

    // Estado para el mes y año seleccionados
    var selectedMonth by remember { mutableStateOf(initialMonth) }
    var selectedYear by remember { mutableStateOf(initialYear) }

    val months = listOf("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")
    val years = (2024..2034).toList().map { it.toString() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFAECB9D)) // Fondo
            .padding(16.dp)
    ) {
        // Logo y título
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.fotoperfil), // Imagen "fotoperfil.jpg"
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )

            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Filtros para Mes y Año
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 110.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Filtro de Mes
                Box(modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)) {
                    DropdownSelector(
                        label = "Mes de consulta",
                        options = months,
                        selectedOption = selectedMonth,
                        onOptionSelected = { selectedMonth = it }  // Actualiza el mes seleccionado
                    )
                }

                // Filtro de Año
                Box(modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)) {
                    DropdownSelector(
                        label = "Año de consulta",
                        options = years,
                        selectedOption = selectedYear,
                        onOptionSelected = { selectedYear = it }  // Actualiza el año seleccionado
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Encabezados de la tabla (centrados)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = "No.", fontSize = 13.sp, modifier = Modifier.weight(0.8f))
                Text(text = "Descripción", fontSize = 13.sp, modifier = Modifier.weight(2.5f))
                Text(text = "Fecha", fontSize = 13.sp, modifier = Modifier.weight(2f))
                Text(text = "Monto", fontSize = 13.sp, modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Transacciones en tarjetas con scroll
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            transactions.forEachIndexed { index, transaction ->
                TransactionCard(
                    number = index + 1,
                    transaction = transaction
                )
            }

            // Ahorro total al final
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Gasto total: $totalgastos",
                    fontSize = 15.sp,
                    modifier = Modifier.padding(10.dp),
                    color = Color.Black
                )
            }
        }

        // Botón de editar justo después de las transacciones
        FloatingActionButton(
            onClick= { /* Acción de editar */ },
            containerColor = Color(0xFF1E3A8A),
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.End)
        ) {
            Text(text = "Editar", color = Color.White)
        }
    }
}

@Composable
fun GastosVarScreen(
    title: String,
    initialMonth: String = "",  // Valor inicial vacío
    initialYear: String = "",   // Valor inicial vacío
    queryDate: String,
    transactions: List<Transaction>
) {
    val scrollState = rememberScrollState()
    var totalgastos = transactions.sumOf { it.amount.toDouble() }

    // Estado para el mes y año seleccionados
    var selectedMonth by remember { mutableStateOf(initialMonth) }
    var selectedYear by remember { mutableStateOf(initialYear) }

    val months = listOf("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")
    val years = (2024..2034).toList().map { it.toString() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFAECB9D)) // Fondo
            .padding(16.dp)
    ) {
        // Logo y título
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.fotoperfil), // Imagen "fotoperfil.jpg"
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )

            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Filtros para Mes y Año
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 110.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Filtro de Mes
                Box(modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)) {
                    DropdownSelector(
                        label = "Mes de consulta",
                        options = months,
                        selectedOption = selectedMonth,
                        onOptionSelected = { selectedMonth = it }  // Actualiza el mes seleccionado
                    )
                }

                // Filtro de Año
                Box(modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)) {
                    DropdownSelector(
                        label = "Año de consulta",
                        options = years,
                        selectedOption = selectedYear,
                        onOptionSelected = { selectedYear = it }  // Actualiza el año seleccionado
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Encabezados de la tabla (centrados)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = "No.", fontSize = 13.sp, modifier = Modifier.weight(0.8f))
                Text(text = "Descripción", fontSize = 13.sp, modifier = Modifier.weight(2.5f))
                Text(text = "Fecha", fontSize = 13.sp, modifier = Modifier.weight(2f))
                Text(text = "Monto", fontSize = 13.sp, modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Transacciones en tarjetas con scroll
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            transactions.forEachIndexed { index, transaction ->
                TransactionCard(
                    number = index + 1,
                    transaction = transaction
                )
            }

            // Ahorro total al final
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Gasto total: $totalgastos",
                    fontSize = 15.sp,
                    modifier = Modifier.padding(10.dp),
                    color = Color.Black
                )
            }
        }

        // Botón de editar justo después de las transacciones
        FloatingActionButton(
            onClick= { /* Acción de editar */ },
            containerColor = Color(0xFF1E3A8A),
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.End)
        ) {
            Text(text = "Editar", color = Color.White)
        }
    }
}

@Composable
fun AhorrosScreen(
    title: String,
    initialMonth: String = "",  // Valor inicial vacío
    initialYear: String = "",   // Valor inicial vacío
    queryDate: String,
    transactions: List<Transaction>
) {
    val scrollState = rememberScrollState()
    var totalahorro = transactions.sumOf { it.amount.toDouble() }

    // Estado para el mes y año seleccionados
    var selectedMonth by remember { mutableStateOf(initialMonth) }
    var selectedYear by remember { mutableStateOf(initialYear) }

    val months = listOf("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")
    val years = (2024..2034).toList().map { it.toString() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFAECB9D)) // Fondo
            .padding(16.dp)
    ) {
        // Logo y título
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.fotoperfil), // Imagen "fotoperfil.jpg"
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )

            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Filtros para Mes y Año
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 110.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Filtro de Mes
                Box(modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)) {
                    DropdownSelector(
                        label = "Mes de consulta",
                        options = months,
                        selectedOption = selectedMonth,
                        onOptionSelected = { selectedMonth = it }  // Actualiza el mes seleccionado
                    )
                }

                // Filtro de Año
                Box(modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)) {
                    DropdownSelector(
                        label = "Año de consulta",
                        options = years,
                        selectedOption = selectedYear,
                        onOptionSelected = { selectedYear = it }  // Actualiza el año seleccionado
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Encabezados de la tabla (centrados)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = "No.", fontSize = 13.sp, modifier = Modifier.weight(0.8f))
                Text(text = "Descripción", fontSize = 13.sp, modifier = Modifier.weight(2.5f))
                Text(text = "Fecha", fontSize = 13.sp, modifier = Modifier.weight(2f))
                Text(text = "Monto", fontSize = 13.sp, modifier = Modifier.weight(1f))
                Text(text = "Total", fontSize = 13.sp, modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Transacciones en tarjetas con scroll
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            transactions.forEachIndexed { index, transaction ->
                TransactionCard(
                    number = index + 1,
                    transaction = transaction
                )
            }

            // Ahorro total al final
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Ahorro total: $totalahorro",
                    fontSize = 15.sp,
                    modifier = Modifier.padding(10.dp),
                    color = Color.Black
                )
            }
        }

        // Botón de editar justo después de las transacciones
        FloatingActionButton(
            onClick= { /* Acción de editar */ },
            containerColor = Color(0xFF1E3A8A),
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.End)
        ) {
            Text(text = "Editar", color = Color.White)
        }
    }
}

@Composable
fun IngresoScreen(){
    var selectedTransaction by remember { mutableStateOf<TransactionType?>(null) }
    var descripcion by remember { mutableStateOf(TextFieldValue("")) }
    var origen by remember { mutableStateOf(TextFieldValue("")) }
    var destino by remember { mutableStateOf(TextFieldValue("")) }
    var monto by remember { mutableStateOf(TextFieldValue("")) }
    var fecha by remember { mutableStateOf(TextFieldValue("")) }
    var expanded by remember { mutableStateOf(false) } // Controla si el menú está desplegado

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFAECB9D)) // Fondo color
            .padding(16.dp)
    ){
    // Título
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA))
    ) {
        Text(
            text = "Ingreso Transacciones",
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Menú desplegable para seleccionar el tipo de transacción
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = true }, // Al hacer clic, mostrar el menú desplegable
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6FA))
    ) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = selectedTransaction?.title ?: "Elegir Transacción",
                fontSize = 18.sp
            )
        }

        androidx.compose.material3.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Ingreso Fijo") },
                onClick = {
                    selectedTransaction = TransactionType.IngresoFijo
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Ingreso Variable") },
                onClick = {
                    selectedTransaction = TransactionType.IngresoVariable
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Gasto Fijo") },
                onClick = {
                    selectedTransaction = TransactionType.GastoFijo
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Gasto Variable") },
                onClick = {
                    selectedTransaction = TransactionType.GastoVariable
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Ahorro") },
                onClick = {
                    selectedTransaction = TransactionType.Ahorro
                    expanded = false
                }
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Campos dinámicos basados en la opción seleccionada
    when (selectedTransaction) {
        is TransactionType.IngresoFijo, is TransactionType.IngresoVariable -> {
            InputField("Descripción", descripcion) { descripcion = it }
            InputField("Origen", origen) { origen = it }
            InputField("Monto", monto) { monto = it }
            InputField("Fecha", fecha) { fecha = it }
        }
        is TransactionType.GastoFijo -> {
            InputField("Descripción", descripcion) { descripcion = it }
            InputField("Destino", destino) { destino = it }
            InputField("Monto", monto) { monto = it }
            InputField("Fecha", fecha) { fecha = it }
        }
        is TransactionType.GastoVariable, is TransactionType.Ahorro -> {
            InputField("Descripción", descripcion) { descripcion = it }
            InputField("Monto", monto) { monto = it }
            InputField("Fecha", fecha) { fecha = it }
        }
        else -> {
            // No se seleccionó ninguna opción
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Botón de agregar
    Button(
        onClick = { /* Acción de editar */ },
        modifier = Modifier.align(Alignment.CenterHorizontally),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3A8A))
    ) {
        Text(text = "Agregar", color = Color.White)
    }
}
}

@Composable
fun InputField(label: String, value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = label, fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    SetupNavGraph(navController = navController)
}
