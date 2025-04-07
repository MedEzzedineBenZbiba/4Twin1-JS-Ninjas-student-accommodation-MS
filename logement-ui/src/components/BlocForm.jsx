import React, { useState } from 'react';
import { add } from '../api/blocApi';
import { TextField, Button, Box } from '@mui/material';

function BlocForm() {
    const [bloc, setBloc] = useState({ nomBloc: '', capaciteBloc: '', latitude: '', longitude: '' });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setBloc(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        await add(bloc);
        setBloc({ nomBloc: '', capaciteBloc: '', latitude: '', longitude: '' });
    };

    return (
        <Box component="form" onSubmit={handleSubmit} sx={{ mb: 4 }}>
            <TextField label="Nom" name="nomBloc" value={bloc.nomBloc} onChange={handleChange} fullWidth sx={{ mb: 2 }} />
            <TextField label="Capacité" name="capaciteBloc" type="number" value={bloc.capaciteBloc} onChange={handleChange} fullWidth sx={{ mb: 2 }} />
            <TextField label="Latitude" name="latitude" value={bloc.latitude} onChange={handleChange} fullWidth sx={{ mb: 2 }} />
            <TextField label="Longitude" name="longitude" value={bloc.longitude} onChange={handleChange} fullWidth sx={{ mb: 2 }} />
            <Button type="submit" variant="contained" color="primary">Ajouter Bloc</Button>
        </Box>
    );
}

export default BlocForm;
