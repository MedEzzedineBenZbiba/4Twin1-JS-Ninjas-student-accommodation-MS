import React, { useState, useEffect } from 'react';
import { Form, Button } from 'react-bootstrap';
import { useNavigate, useParams } from 'react-router-dom';
import { getBlocById, addBloc, updateBloc } from '../services/BlocService';

function BlocForm() {
    const [bloc, setBloc] = useState({
        nomBloc: '',
        capaciteBloc: '',
        foyerId: '',
    });
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const { id } = useParams();
    const isEdit = Boolean(id);

    useEffect(() => {
        if (isEdit) {
            setLoading(true);
            getBlocById(id)
                .then((res) => {
                    setBloc(res.data);
                })
                .catch((err) => {
                    console.error(err);
                    alert('Erreur lors du chargement du bloc.');
                })
                .finally(() => setLoading(false));
        }
    }, [id, isEdit]);

    const handleChange = (e) =>
        setBloc({ ...bloc, [e.target.name]: e.target.value });

    const handleSubmit = (e) => {
        e.preventDefault();
        setLoading(true);

        const action = isEdit ? updateBloc : addBloc;
        const payload = {
            ...bloc,
            capaciteBloc: parseInt(bloc.capaciteBloc), // Ensure capaciteBloc is a number
        };

        action(payload)
            .then(() => {
                navigate('/blocs');
            })
            .catch((err) => {
                console.error(err);
                alert(isEdit ? 'Erreur lors de la mise à jour du bloc.' : 'Erreur lors de l\'ajout du bloc.');
            })
            .finally(() => setLoading(false));
    };

    return (
        <div className="container mt-4">
            <h2>{isEdit ? 'Modifier Bloc' : 'Ajouter Bloc'}</h2>
            {loading ? (
                <div>Chargement...</div>
            ) : (
                <Form onSubmit={handleSubmit}>
                    <Form.Group className="mb-3">
                        <Form.Label>Nom du Bloc</Form.Label>
                        <Form.Control
                            type="text"
                            name="nomBloc"
                            value={bloc.nomBloc}
                            onChange={handleChange}
                            required
                        />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Capacité du Bloc</Form.Label>
                        <Form.Control
                            type="number"
                            name="capaciteBloc"
                            value={bloc.capaciteBloc}
                            onChange={handleChange}
                            required
                        />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>ID du Foyer (facultatif)</Form.Label>
                        <Form.Control
                            type="number"
                            name="foyerId"
                            value={bloc.foyerId || ''}
                            onChange={handleChange}
                            placeholder="Entrez l'ID du foyer pour l'assigner"
                        />
                    </Form.Group>
                    <Button variant="primary" type="submit" disabled={loading}>
                        {isEdit ? 'Mettre à jour' : 'Ajouter'}
                    </Button>
                </Form>
            )}
        </div>
    );
}

export default BlocForm;