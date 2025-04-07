import React, { useState, useEffect } from 'react';
import { getAllChambres, deleteChambre } from '../api/chambreApi';
import { Button, CircularProgress, Alert, Box } from '@mui/material';

function ChambreList({ onEdit }) {
    const [chambres, setChambres] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchChambres();
    }, []);

    const fetchChambres = async () => {
        try {
            setLoading(true);
            setError(null);
            const response = await getAllChambres();
            setChambres(response.data);
        } catch (error) {
            console.error('Error fetching chambres:', error);
            setError('Failed to load chambres. Please try again later.');
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        try {
            await deleteChambre(id);
            fetchChambres();
        } catch (error) {
            console.error('Error deleting chambre:', error);
            setError('Failed to delete chambre. Please try again.');
        }
    };

    if (loading) {
        return <Box display="flex" justifyContent="center" mt={4}><CircularProgress /></Box>;
    }

    if (error) {
        return <Alert severity="error" sx={{ mt: 4 }}>{error}</Alert>;
    }

    return (
        <div>
            <h2>Chambre List</h2>
            {chambres.length === 0 ? (
                <p>No chambres found.</p>
            ) : (
                chambres.map(chambre => (
                    <div key={chambre.id}>
                        <p>
                            Number: {chambre.numeroChambre} -
                            Type: {chambre.typeChambre}
                        </p>
                        <Button onClick={() => onEdit(chambre)} variant="outlined" color="primary" sx={{ mr: 1 }}>
                            Edit
                        </Button>
                        <Button onClick={() => handleDelete(chambre.id)} variant="outlined" color="error">
                            Delete
                        </Button>
                    </div>
                ))
            )}
        </div>
    );
}

export default ChambreList;