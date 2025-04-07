import React, { useEffect, useState } from 'react';
import { getWeatherByBlocId } from '../api/blocApi';

function BlocWeather({ blocId }) {
    const [weather, setWeather] = useState(null);

    useEffect(() => {
        if (blocId) {
            getWeatherByBlocId(blocId).then(res => setWeather(res.data));
        }
    }, [blocId]);

    if (!weather) return null;

    return (
        <div>
            <h3>Météo</h3>
            <p>Lieu: {weather.lieu}</p>
            <p>Température: {weather.température}</p>
            <p>Humidité: {weather.humidité}</p>
            <p>Description: {weather.description}</p>
        </div>
    );
}

export default BlocWeather;