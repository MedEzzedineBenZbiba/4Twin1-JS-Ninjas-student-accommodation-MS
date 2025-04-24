import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import {
    Box,
    Button,
    TextField,
    Typography,
    Paper,
    Avatar,
    InputAdornment,
    IconButton,
    CircularProgress,
    CssBaseline,
    Container
} from "@mui/material";
import {
    LockOutlined as LockIcon,
    Visibility,
    VisibilityOff
} from "@mui/icons-material";

const Login = () => {
    const [username, setUsername] = useState("admin");
    const [password, setPassword] = useState("admin");
    const [error, setError] = useState("");
    const [showPassword, setShowPassword] = useState(false);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleLogin = async () => {
        setError("");
        setLoading(true);
        try {
            const data = new URLSearchParams();
            data.append("client_id", "apiGateway");
            data.append("username", username);
            data.append("password", password);
            data.append("grant_type", "password");
            // Add client_secret if required
            // data.append("client_secret", "your-client-secret");

            const response = await axios.post(
                "http://localhost:8080/realms/student_accommodation/protocol/openid-connect/token",
                data,
                {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                        "Accept": "application/json",
                    },
                }
            );

            localStorage.setItem("token", response.data.access_token);
            navigate("/universite");
        } catch (err) {
            if (err.response) {
                setError("Échec de la connexion : " + (err.response.data.error_description || "Identifiants invalides"));
            } else if (err.request) {
                setError("Erreur réseau : Impossible de contacter le serveur d'authentification.");
            } else {
                setError("Erreur : " + err.message);
            }
        } finally {
            setLoading(false);
        }
    };

    const handleKeyPress = (e) => {
        if (e.key === "Enter") {
            handleLogin();
        }
    };

    return (
        <>
            <CssBaseline />
            <Container
                component="main"
                maxWidth="xs"
                sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    justifyContent: 'center',
                    minHeight: '100vh',
                    py: 4
                }}
            >
                <Paper
                    elevation={3}
                    sx={{
                        p: 4,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                        width: '100%',
                        borderRadius: 2,
                        boxShadow: '0px 4px 20px rgba(0, 0, 0, 0.1)'
                    }}
                >
                    <Avatar
                        sx={{
                            m: 1,
                            bgcolor: 'primary.main',
                            width: 60,
                            height: 60
                        }}
                    >
                        <LockIcon fontSize="medium" />
                    </Avatar>

                    <Typography component="h1" variant="h5" sx={{ mb: 3, fontWeight: 600 }}>
                        Connexion
                    </Typography>

                    <Box
                        component="form"
                        sx={{
                            width: '100%',
                            mt: 1
                        }}
                    >
                        <TextField
                            margin="normal"
                            fullWidth
                            label="Nom d'utilisateur"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            onKeyPress={handleKeyPress}
                            autoFocus
                            variant="outlined"
                            sx={{ mb: 2 }}
                        />

                        <TextField
                            margin="normal"
                            fullWidth
                            label="Mot de passe"
                            type={showPassword ? "text" : "password"}
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            onKeyPress={handleKeyPress}
                            variant="outlined"
                            sx={{ mb: 1 }}
                            InputProps={{
                                endAdornment: (
                                    <InputAdornment position="end">
                                        <IconButton
                                            onClick={() => setShowPassword(!showPassword)}
                                            edge="end"
                                            aria-label="toggle password visibility"
                                        >
                                            {showPassword ? <VisibilityOff /> : <Visibility />}
                                        </IconButton>
                                    </InputAdornment>
                                )
                            }}
                        />

                        <Button
                            fullWidth
                            variant="contained"
                            onClick={handleLogin}
                            disabled={loading}
                            sx={{
                                mt: 3,
                                mb: 2,
                                py: 1.5,
                                fontSize: '1rem',
                                textTransform: 'none',
                                borderRadius: 1
                            }}
                            size="large"
                        >
                            {loading ? (
                                <CircularProgress size={24} color="inherit" />
                            ) : (
                                "Se connecter"
                            )}
                        </Button>

                        {error && (
                            <Typography
                                color="error"
                                align="center"
                                sx={{ mt: 2 }}
                            >
                                {error}
                            </Typography>
                        )}
                    </Box>
                </Paper>
            </Container>
        </>
    );
};

export default Login;