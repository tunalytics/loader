SELECT tr.name as track_name, tr.length as song_length, 
aat.name as artist_type, ar.name as artist_area, 
g.name as artist_gender,a.sort_name as artist_name, 
rec.name as recording_name,reca.end_date_year as recording_year,
rat.name as recording_type from musicbrainz.track tr 
JOIN artist_credit ac ON tr.artist_credit=ac.id 
JOIN artist_credit_name acn ON ac.id = acn.artist_credit
JOIN artist a ON a.id = acn.artist 
JOIN artist_alias_type aat ON a.type = aat.id
JOIN gender g ON g.id = a.gender
JOIN area ar ON ar.id = a.area 
JOIN recording rec ON rec.id = tr.recording 
JOIN recording_alias reca ON reca.recording = rec.id 
JOIN recording_alias_type rat ON reca.type = rat.id LIMIT 1000;
