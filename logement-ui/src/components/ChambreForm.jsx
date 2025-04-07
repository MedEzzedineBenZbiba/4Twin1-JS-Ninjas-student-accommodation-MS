import React, { useEffect, useState } from 'react';
import { addChambre, updateChambre } from '../api/chambreApi';
import { TextField, Button, Box, MenuItem } from '@mui/material';

const typeOptions = ['SIMPLE', 'DOUBLE', 'TRIPLE'];

function ChambreForm({ chambreToEdit }) {
    const [chambre, setChambre] = useState({ numeroChambre: '', typeChambre: 'SIMPLE' });

    useEffect(() => {
        if (chambreToEdit) {
            setChambre(chambreToEdit);
        }
    }, [chambreToEdit]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setChambre(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (chambre.id) {
            await updateChambre(chambre);
        } else {
            await addChambre(chambre);
        }
        setChambre({ numeroChambre: '', typeChambre: 'SIMPLE' });
    };

    return (
        <Box component="form" onSubmit={handleSubmit} sx={{ mb: 4 }}>
            <TextField
                label="Numéro"
                name="numeroChambre"
                value={chambre.numeroChambre}
                onChange={handleChange}
                fullWidth
                sx={{ mb: 2 }}
            />
            <TextField
                select
                label="Type"
                name="typeChambre"
                value={chambre.typeChambre}
                onChange={handleChange}
                fullWidth
                sx={{ mb: 2 }}
            >
                {typeOptions.map(type => (
                    <MenuItem key={type} value={type}>{type}</MenuItem>
                ))}
            </TextField>
            <Button type="submit" variant="contained" color="primary">
                {chambre.id ? 'Modifier' : 'Ajouter'} Chambre
            </Button>
        </Box>
    );
}

export default ChambreForm;
